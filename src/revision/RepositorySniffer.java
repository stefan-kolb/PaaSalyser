package revision;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class RepositorySniffer {

	private static final String remotePath = "git@github.com:stefan-kolb/paas-profiles.git";
	private static final File localPath = new File("paas-profiles");

	List<ObjectId> profileChangedCommits;

	public RepositorySniffer() throws NoHeadException, GitAPIException, IncorrectObjectTypeException, IOException {

		if (!localPath.exists()) {
			// Clone Repository if it does not already exist
			cloneRepository();
		} else {
			System.out.println(localPath + " already exists.");
		}

		try (Git git = Git.open(localPath); Repository repository = git.getRepository()) {
			System.out.println(git);

			profileChangedCommits = scanRepositoryForProfilesCommits(git);

		}

		// Write Code here

	}

	private void cloneRepository() throws InvalidRemoteException, TransportException, GitAPIException {
		Git.cloneRepository().setURI(remotePath).setDirectory(localPath).call();
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

}
