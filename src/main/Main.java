package main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import gsonUtility.GsonAdapter;
import profile.PaasProfile;
import report.Report;
import statistics.DataPreprocessing;
import statistics.StatisticsImplWithModels;

public class Main {

	public static void main(String[] args) {
		Path directoryToScan = Paths.get("PaasProfiles");
		Path outputPath = Paths.get("Reports/PaaSReport_"
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss")) + ".json");
		try {

			// This is where the magic happens.
			evaluateDirectory(directoryToScan, outputPath);

			System.out.println("Finished scanning.");
		} catch (IOException e) {
			System.out.println("A Problem occured while scanning: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("Exit.");
			System.exit(0);
		}
	}

	private static void evaluateDirectory(Path directory, Path outputPath) throws IOException {
		List<PaasProfile> profilesList = new LinkedList<PaasProfile>();

		profilesList = GsonAdapter.scanDirectoryForJsonFiles(Paths.get("PaasProfiles"));
		System.out.println("yes");
		for (PaasProfile profile : profilesList) {
			if (profile.isFailed() == true) {
				throw new IOException("Failed to scan profiles at: " + profile.getName());
			}
		}

		generateReport(profilesList, outputPath);
	}

	private static void generateReport(List<PaasProfile> profilesList, Path outputPath) throws IOException {
		profilesList.forEach(System.out::println);
		System.out.println("1");
		
		DataPreprocessing dataPreprocessing = new DataPreprocessing(profilesList);
		System.out.println(dataPreprocessing);
		System.out.println("2");
		
		StatisticsImplWithModels statistics = new StatisticsImplWithModels(dataPreprocessing);
		System.out.println(statistics);
		
		Report report = new Report(statistics);
		System.out.println(report);
		System.out.println("3");

		GsonAdapter.createReportAsJsonFile(report, outputPath);
		System.out.println("4");
	}
}
