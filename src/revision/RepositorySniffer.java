package revision;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Repository;

public class RepositorySniffer {

	private static final String remotePath = "git@github.com:stefan-kolb/paas-profiles.git";
	private static final File localPath = new File("paas-profiles");

	private Git git;
	private Repository repository;

	public RepositorySniffer() {

		if (localPath.exists()) {
			System.out.println(localPath + " already exists.");
			try {
				git = Git.open(localPath);
				repository = git.getRepository();
				System.out.println(git);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Cloning from " + remotePath + " to " + localPath);
			try (Git git = cloneRepository()) {
				repository = git.getRepository();
				System.out.println("Having repository: " + git.getRepository().getDirectory());

			} catch (InvalidRemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Write Code here
		
	}

	private Git cloneRepository() throws InvalidRemoteException, TransportException, GitAPIException {
		return Git.cloneRepository().setURI(remotePath).setDirectory(localPath).call();
	}

}
