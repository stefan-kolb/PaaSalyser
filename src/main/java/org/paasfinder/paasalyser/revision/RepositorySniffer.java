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
import org.paasfinder.paasalyser.database.DatabaseConnector;
import org.paasfinder.paasalyser.gsonutility.GsonAdapter;
import org.paasfinder.paasalyser.profile.PaasProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositorySniffer implements AutoCloseable {

	private final Logger logger = LoggerFactory.getLogger(RepositorySniffer.class);

	private final String gitRemotePath;
	private final Path pathOfRepository;

	private final GsonAdapter gsonAdapter;
	private final Git git;

	private DatabaseConnector database;

	public RepositorySniffer(GsonAdapter gsonAdapter, String gitRemotePath, String pathOfRepository,
			DatabaseConnector database) throws IOException, GitAPIException {
		this.gsonAdapter = gsonAdapter;
		this.gitRemotePath = gitRemotePath;
		this.pathOfRepository = Paths.get(pathOfRepository);
		this.database = database;
		git = initializeOrResetRepository();
	}

	@Override
	public void close() throws IllegalStateException {
		git.close();
	}

	public Map<RevCommit, List<PaasProfile>> sniffRepository() throws IOException, GitAPIException {
		logger.info("Resetting and pulling repository");
		resetAndPullRepository();

		return saveRelevantCommits(scanRepositoryForRelevantCommits());
	}

	public Map.Entry<RevCommit, List<PaasProfile>> getStateOfTheArt() throws GitAPIException, IOException {
		RevCommit currentCommit = git.log().call().iterator().next();
		logger.info("State of the art commit is: " + currentCommit.getName());
		return new AbstractMap.SimpleEntry<RevCommit, List<PaasProfile>>(currentCommit,
				getProfilesOfCommit(currentCommit.getName()));
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
		// index-lock ensures synchronized acces by multiple threads
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

	private List<RevCommit> scanRepositoryForRelevantCommits()
			throws IncorrectObjectTypeException, IOException, GitAPIException {
		logger.info("Scanning repository for relevant commits");
		List<RevCommit> profileChangedCommits = new ArrayList<>();
		Iterable<RevCommit> commits = git.log().all().call();

		ObjectId currentCommit = null;
		ObjectId commitBeforeCurrent = null;

		for (RevCommit commit : commits) {
			currentCommit = commit.getTree();
			if (commitBeforeCurrent != null)
				try (ObjectReader reader = git.getRepository().newObjectReader()) {
					CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
					oldTreeIter.reset(reader, commitBeforeCurrent);
					CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
					newTreeIter.reset(reader, currentCommit);

					// get the list of changed files
					List<DiffEntry> diffs = git.diff().setNewTree(newTreeIter).setOldTree(oldTreeIter).call();
					for (DiffEntry entry : diffs) {
						if (entry.toString().contains("profiles/")) {
							if (!profileChangedCommits.contains(commit))
								profileChangedCommits.add(commit);
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

	private Map<RevCommit, List<PaasProfile>> saveRelevantCommits(List<RevCommit> profileChangedCommits) {
		Map<RevCommit, List<PaasProfile>> profilesOfCommits = new HashMap<>();

		logger.info("Scanning relevant commits");
		for (RevCommit commit : profileChangedCommits) {

//			if (commit.getName().equals("7f132fabb4e220f794b4926309dc8d48c794768c")) {
//				logger.info("Initial commit reached");
//				continue;
//			}
			if (database.contains(commit.getName())) {
				logger.info("Commit already in Datastore");
				continue;
			}

			try {
				logger.info("Retrieving profiles of: " + commit.getName());
				List<PaasProfile> profiles = getProfilesOfCommit(commit.getName());
				if (profiles != null) {
					profilesOfCommits.putIfAbsent(commit, profiles);
				} else {
					logger.error("Could not retrieve profiles of " + commit.getName());
				}
			} catch (GitAPIException e) {
				logger.error("GitAPIException occurred while trying to store Profiles of current commit-"
						+ commit.getName());
			} catch (IOException e) {
				logger.error(
						"IOException occurred while trying to store Profiles of current commit-" + commit.getName());
			}
		}
		logger.info("Finished scanning relevant commits");
		return profilesOfCommits;
	}

	private List<PaasProfile> getProfilesOfCommit(String commitId) throws GitAPIException, IOException {
		git.checkout().setName(commitId).call();
		Path path = Paths.get(pathOfRepository.toString() + "/profiles");
		List<PaasProfile> profiles = gsonAdapter.scanDirectoryForJsonFiles(path);
		for (PaasProfile profile : profiles) {
			if (profile == null) {
				// Skip profiles if profiles are invalid to ensure data
				// consistency across all profiles generated
				return null;
			}
		}
		return profiles;
	}

}
