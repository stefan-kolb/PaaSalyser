package revision;

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

import gsonutility.GsonAdapter;
import profile.PaasProfile;

public class RepositorySniffer {

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
		initializeOrPullRepository();

		try (Git git = Git.open(pathOfProfilesRepository)) {
			// Repository repository = git.getRepository();
			System.out.println(git);

			profileChangedCommits = scanRepositoryForProfilesCommits(git);

			System.out.println("Profiles:");
			iterateCommits(git, this.gsonAdapter);
		}
	}

	private void initializeOrPullRepository() throws IOException, GitAPIException {
		if (!pathOfProfilesRepository.exists()) {
			// Clone Repository if it does not already exist
			cloneRepository();
		} else {
			System.out.println(pathOfProfilesRepository + " already exists.");
			try (Git git = Git.open(pathOfProfilesRepository)) {
				System.out.println("Resetting.");
				git.reset().setMode(ResetType.HARD).call();
				pullRepository(git);
			}
		}
	}

	private void cloneRepository() throws GitAPIException {
		System.out.println("Cloning Repository: " + gitRemotePath);
		Git.cloneRepository().setURI(gitRemotePath).setDirectory(pathOfProfilesRepository).call();
	}

	private void pullRepository(Git git) throws GitAPIException {
		System.out.println("Pulling from Repository: " + gitRemotePath);
		git.checkout().setName("master").call();
		git.pull().call();

		System.out.println("Pulling finished.");
	}

	private List<ObjectId> scanRepositoryForProfilesCommits(Git git)
			throws IncorrectObjectTypeException, IOException, GitAPIException {
		List<ObjectId> profileChangedCommits = new ArrayList<>();
		Iterable<RevCommit> commits = git.log().all().call();

		ObjectId currentCommit = null;
		ObjectId commitBeforeCurrent = null;

		System.out.println("Printing diff between tree: " + commitBeforeCurrent + " and " + currentCommit);
		for (RevCommit commit : commits) {
			// Set the current commit
			currentCommit = commit.getTree();
			System.out.println(commit.getShortMessage());
			if (commitBeforeCurrent != null)
				try (ObjectReader reader = git.getRepository().newObjectReader()) {
					CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
					oldTreeIter.reset(reader, commitBeforeCurrent);
					CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
					newTreeIter.reset(reader, currentCommit);

					// finally get the list of changed files
					List<DiffEntry> diffs = git.diff().setNewTree(newTreeIter).setOldTree(oldTreeIter).call();
					for (DiffEntry entry : diffs) {
						System.out.println("Entry: " + entry.toString());
						if (entry.toString().contains("profiles/")) {
							if (!profileChangedCommits.contains(commit.getId()))
								profileChangedCommits.add(commit.getId());
						}
					}
				}
			System.out.println();
			// Set the current commit to the one before for getting the
			// difference inbetween
			commitBeforeCurrent = currentCommit;
		}

		System.out.println("Number of relevant commits: " + profileChangedCommits.size());
		profileChangedCommits.forEach(System.out::println);

		return profileChangedCommits;
	}

	private void iterateCommits(Git git, GsonAdapter gsonAdapter) {
		profileChangedCommits.forEach(commit -> {
			try {
				storeProfilesOfCommit(git, commit.getName(), gsonAdapter);
			} catch (GitAPIException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		profilesLock = false;
	}

	private void storeProfilesOfCommit(Git git, String commitId, GsonAdapter gsonAdapter)
			throws GitAPIException, IOException {
		System.out.println();
		System.out.println("Current Commit is: " + commitId);
		git.checkout().setName(commitId).call();

		Path path = Paths.get(pathOfProfilesRepository.toString() + "/profiles");

		// List<PaasProfile> profilesOfCurrentCommit =
		// gsonAdapter.scanDirectoryForJsonFiles(path);
		profilesOfCommits.put(commitId, gsonAdapter.scanDirectoryForJsonFiles(path));

		// Hard reset to undo all changes made
		System.out.println("Resetting.");
		git.reset().setMode(ResetType.HARD).call();

		System.out.println("Number of profiles in this commit: " + profilesOfCommits.get(commitId).size());
	}

}
