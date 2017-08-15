package org.paasfinder.paasalyser.revision;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.paasfinder.paasalyser.gsonutility.GsonAdapter;
import org.paasfinder.paasalyser.profile.PaasProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositorySniffer implements AutoCloseable {

	private final Logger logger = LoggerFactory.getLogger(RepositorySniffer.class);

	private final String gitRemotePath;
	private final Path pathOfRepository;

	private List<ObjectId> profileChangedCommits;

	private final GsonAdapter gsonAdapter;
	private final Git git;

	public RepositorySniffer(GsonAdapter gsonAdapter, String gitRemotePath, String pathOfRepository)
			throws IOException, GitAPIException {
		this.gsonAdapter = gsonAdapter;
		this.gitRemotePath = gitRemotePath;
		this.pathOfRepository = Paths.get(pathOfRepository);
		git = initializeOrResetRepository();
	}

	@Override
	public void close() throws IllegalStateException {
		git.close();
	}

	public Map<String, List<PaasProfile>> sniffRepository() throws IOException, GitAPIException {
		resetAndPullRepository();

		logger.info("Scanning Profiles for relevant commits");
		profileChangedCommits = scanRepositoryForProfilesCommits();

		logger.info("Finished sniffing");
		return scanCommits();
	}

	public Map.Entry<String, List<PaasProfile>> getStateOfTheArt() throws GitAPIException, IOException {
		String currentCommitName = git.log().call().iterator().next().getName();
		logger.info("Current commit is: " + currentCommitName);
		Path outputPath = Paths.get("Reports/PaasReport_" + currentCommitName + ".json");

		if (Files.exists(outputPath)) {
			logger.info("Report of state of the art (commit id: " + currentCommitName + ") already existing");
		}
		return new AbstractMap.SimpleEntry<String, List<PaasProfile>>(currentCommitName,
				getProfilesOfCommit(currentCommitName));
	}

	/**
	 * Clones or reset and pulls the repository.
	 * 
	 * @return Git object of the repository
	 * @throws IOException
	 *             if Output folder is not valid
	 * @throws GitAPIException
	 *             If problems with git occurred
	 */
	private Git initializeOrResetRepository() throws IOException, GitAPIException {
		logger.info("Initialising Repository");
		if (Files.deleteIfExists(Paths.get("paas-profiles/.git/index.lock"))) {
			logger.info("Removing git HEAD.lock");
		}
		if (Files.exists(pathOfRepository) && Files.walk(pathOfRepository, 1).mapToLong(entry -> 1).sum() > 1) {
			return resetAndPullRepository();
		} else {
			return cloneRepository();
		}
	}

	private Git cloneRepository() throws GitAPIException {
		logger.info("Cloning repository: " + gitRemotePath);
		return Git.cloneRepository().setURI(gitRemotePath).setDirectory(new File(pathOfRepository.toString())).call();
	}

	private Git resetAndPullRepository() throws IOException, CheckoutConflictException, GitAPIException {
		logger.info("Resetting & Pulling repository: " + gitRemotePath);
		try (Git git = Git.open(new File(pathOfRepository.toString()))) {
			git.reset().setMode(ResetType.HARD).call();
			git.checkout().setName("master").call();
			git.pull().call();
			logger.info("Resetting & Pulling finished");
			return git;
		}
	}

	private List<ObjectId> scanRepositoryForProfilesCommits()
			throws IncorrectObjectTypeException, IOException, GitAPIException {
		logger.info("Scanning repository for relevant commits");
		List<ObjectId> profileChangedCommits = new ArrayList<>();
		Iterable<RevCommit> commits = git.log().all().call();

		ObjectId currentCommit = null;
		ObjectId commitBeforeCurrent = null;

		for (RevCommit commit : commits) {
			currentCommit = commit.getTree();
			logger.info("Printing diff between tree: " + commitBeforeCurrent + " and " + currentCommit);
			if (commitBeforeCurrent != null)
				try (ObjectReader reader = git.getRepository().newObjectReader()) {
					CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
					oldTreeIter.reset(reader, commitBeforeCurrent);
					CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
					newTreeIter.reset(reader, currentCommit);

					// finally get the list of changed files
					List<DiffEntry> diffs = git.diff().setNewTree(newTreeIter).setOldTree(oldTreeIter).call();
					for (DiffEntry entry : diffs) {
						if (entry.toString().contains("profiles/")) {
							if (!profileChangedCommits.contains(commit.getId()))
								profileChangedCommits.add(commit.getId());
						}
					}
				}
			// Set the current commit to the one before for getting the
			// difference inbetween
			commitBeforeCurrent = currentCommit;
		}
		logger.info("Number of relevant commits: " + profileChangedCommits.size());
		return profileChangedCommits;
	}

	private Map<String, List<PaasProfile>> scanCommits() {
		Map<String, List<PaasProfile>> profilesOfCommits = new HashMap<>();

		logger.info("Scanning relevant commits");
		profileChangedCommits.forEach(commit -> {

			if (commit.getName().contains("7f132fabb4e220f794b4926309dc8d48c794768c")) {
				logger.info("Initial commit reached");
				return;
			}

			try {
				logger.info("Current commit is: " + commit.getName());
				List<PaasProfile> profiles = getProfilesOfCommit(commit.getName());
				if (profiles != null) {
					profilesOfCommits.putIfAbsent(commit.getName(), profiles);
				}
			} catch (GitAPIException e) {
				logger.error("GitAPIException occurred while trying to store Profiles of current commit-"
						+ commit.getName());
			} catch (IOException e) {
				logger.error(
						"IOException occurred while trying to store Profiles of current commit-" + commit.getName());
			}
		});
		logger.info("Finished scanning relevant commits");
		return profilesOfCommits;
	}

	private List<PaasProfile> getProfilesOfCommit(String commitId) throws GitAPIException, IOException {
		git.checkout().setName(commitId).call();

		Path path = Paths.get(pathOfRepository.toString() + "/profiles");

		List<PaasProfile> profiles = gsonAdapter.scanDirectoryForJsonFiles(path);

		if (profiles.contains(null)) {
			// If errors occurred during scanning, do not store this commit!
			profiles.forEach(System.out::println);
			return null;
		} else
			return profiles;
	}

}
