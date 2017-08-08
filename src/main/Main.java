package main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;

import gsonutility.GsonAdapter;
import profile.PaasProfile;
import report.Report;
import revision.RepositorySniffer;
import statistics.DataPreprocessing;
import statistics.Statistics;

public class Main {

	static GsonAdapter gsonAdapter = new GsonAdapter();

	public static void main(String[] args) {
		System.out.println("Starting Execution.");

		Path directoryToScan = Paths.get("paas-profiles/profiles");
		Path outputPath = Paths.get("Reports/PaaSReport_"
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss")) + ".json");

		final String gitRemotePath = "git@github.com:stefan-kolb/paas-profiles.git";
		final String pathOfProfilesRepository = "paas-profiles";

//		try {
//			new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository).sniff();
//		} catch (GitAPIException | IOException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}

		try {
			new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository).initializeOrResetRepository();
			// This is where the magic happens.
			evaluateDirectory(directoryToScan, outputPath);

			System.out.println();
			System.out.println("Finished creating: " + outputPath);
			System.exit(0);
		} catch (IOException e) {
			System.out.println();
			System.out.println("A Problem occured while scanning: " + e.getMessage());
			System.exit(1);
		} catch (GitAPIException e) {
			System.out.println();
			System.out.println("A Problem occured while scanning: " + e.getMessage());
			System.exit(1);
		}
	}

	private static void evaluateDirectory(Path directory, Path outputPath) throws IOException {
		List<PaasProfile> profilesList = new ArrayList<PaasProfile>();

		profilesList = gsonAdapter.scanDirectoryForJsonFiles(Paths.get("PaasProfiles"));
		for (PaasProfile profile : profilesList) {
			if (profile.isFailed() == true) {
				throw new IOException("Failed to scan profiles at: " + profile.getName());
			}
		}

		generateReport(profilesList, outputPath);
	}

	private static void generateReport(List<PaasProfile> profilesList, Path outputPath) throws IOException {
		System.out.println();
		System.out.println("---Preprocessing---");
		DataPreprocessing dataPreprocessing = new DataPreprocessing(profilesList);

		System.out.println();
		System.out.println("---Statistics---");
		Statistics statistics = new Statistics(dataPreprocessing);

		System.out.println();
		System.out.println("---Creating Report---");
		Report report = new Report(statistics);

		System.out.println();
		System.out.println("---Writing to File---");
		gsonAdapter.createReportAsJsonFile(report, outputPath);
	}
}
