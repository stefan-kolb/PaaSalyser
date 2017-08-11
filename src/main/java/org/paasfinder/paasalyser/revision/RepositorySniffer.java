package org.paasfinder.paasalyser.revision;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
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

public class RepositorySniffer {

	private final Logger logger = LoggerFactory.getLogger(RepositorySniffer.class);

	private String gitRemotePath;
	private File pathOfProfilesRepository;

	private List<ObjectId> profileChangedCommits;

	private GsonAdapter gsonAdapter;

	private Map<String, List<PaasProfile>> profilesOfCommits;
	private boolean profilesLock = true;

	public RepositorySniffer(GsonAdapter gsonAdapter, String gitRemotePath, String pathOfProfilesRepository) {
		this.gsonAdapter = gsonAdapter;
		this.gitRemotePath = gitRemotePath;
		this.pathOfProfilesRepository = new File(pathOfProfilesRepository);
		profilesOfCommits = new HashMap<>();
	}

	public Map<String, List<PaasProfile>> getProfilesOfCommits() {
		if (profilesLock == false)
			return profilesOfCommits;
		else
			return null;
	}

	public void sniff() throws IOException, GitAPIException {
		initializeOrResetRepository();

		try (Git git = Git.open(pathOfProfilesRepository)) {
			// Repository repository = git.getRepository();

			logger.info("Scanning Profiles for relevant commits");
			profileChangedCommits = scanRepositoryForProfilesCommits(git);

			iterateCommits(git, this.gsonAdapter);

			logger.info("Resetting & pulling repository");
			git.reset().setMode(ResetType.HARD).call();
			git.checkout().setName("master").call();
			git.pull().call();
			logger.info("Finished sniffing");
		}
	}

	/**
	 * Clones or reset and pulls the repository.
	 * 
	 * @return Git object
	 * @throws IOException
	 *             if Output folder is not valid
	 * @throws GitAPIException
	 *             If problems with git occurred
	 */
	public Git initializeOrResetRepository() throws IOException, GitAPIException {
		if (!pathOfProfilesRepository.exists()) {
			return cloneRepository();
		}
		return resetAndPullRepository();
	}

	private Git cloneRepository() throws GitAPIException {
		logger.info("Cloning repository: " + gitRemotePath);
		return Git.cloneRepository().setURI(gitRemotePath).setDirectory(pathOfProfilesRepository).call();
	}

	private Git resetAndPullRepository() throws GitAPIException, IOException {
		logger.info("Resetting & Pulling repository: " + gitRemotePath);
		try (Git git = Git.open(pathOfProfilesRepository)) {
			git.reset().setMode(ResetType.HARD).call();
			git.checkout().setName("master").call();
			git.pull().call();
			logger.info("Resetting & Pulling finished");
			return git;
		}
	}

	private List<ObjectId> scanRepositoryForProfilesCommits(Git git)
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

	private void iterateCommits(Git git, GsonAdapter gsonAdapter) {
		logger.info("Iterating relevant commits");
		profileChangedCommits.forEach(commit -> {

			if (commit.getName().contains("7f132fabb4e220f794b4926309dc8d48c794768c")) {
				logger.info("Initial commit reached");
				profilesLock = false;
				return;
			}

			try {
				storeProfilesOfCommit(git, commit.getName(), gsonAdapter);
			} catch (GitAPIException e) {
				logger.error("GitAPIException occurred during iterating commits");
			} catch (IOException e) {
				logger.error("IOException occurred during iterating commits");
			}
		});
	}

	private void storeProfilesOfCommit(Git git, String commitId, GsonAdapter gsonAdapter)
			throws GitAPIException, IOException {
		logger.info("Current Commit is: " + commitId);
		git.checkout().setName(commitId).call();

		Path path = Paths.get(pathOfProfilesRepository.toString() + "/profiles");

		List<PaasProfile> profiles = gsonAdapter.scanDirectoryForJsonFiles(path);

		if (profiles.contains(null)) {
			// If errors occurred during scanning, do not store this commit!
			return;
		}

		profilesOfCommits.put(commitId, profiles);

		git.reset().setMode(ResetType.HARD).call();
	}

}
