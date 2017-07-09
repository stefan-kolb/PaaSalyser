package main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import gsonUtility.GsonAdapter;
import models.PaasProfile;
import report.models.supermodels.Report;
import statistics.DataPreprocessing;
import statistics.DataPreprocessingImpl;
import statistics.Statistics;
import statistics.StatisticsImpl;

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

		for (PaasProfile profile : profilesList) {
			if (profile.isFailed() == true) {
				throw new IOException("Failed to scan profiles at: " + profile.getName());
			}
		}
		
		generateReport(profilesList, outputPath);
	}

	private static void generateReport(List<PaasProfile> profilesList, Path outputPath) throws IOException {
		DataPreprocessing dataPreprocessor = new DataPreprocessingImpl();
		Statistics statistics = new StatisticsImpl();

		Report report = new Report(statistics.evalRevision(dataPreprocessor.evalRevision(profilesList)),
				statistics.evalStatus(dataPreprocessor.evalStatus(profilesList)),
				statistics.evalStatusSince(dataPreprocessor.evalStatusSince(profilesList)),
				statistics.evalType(dataPreprocessor.evalType(profilesList)),
				statistics.evalQos(dataPreprocessor.evalQos(profilesList)),
				statistics.evalOverallCompliance(dataPreprocessor.evalCompliance()),
				statistics.evalSpecificCompliance(dataPreprocessor.evalCompliance()),
				statistics.evalPlatform(dataPreprocessor.evalPlatform(profilesList)),
				statistics.evalHosting(dataPreprocessor.evalHosting(profilesList)),
				statistics.evalPricing(dataPreprocessor.evalPricing(profilesList)),
				statistics.evalScaling(dataPreprocessor.evalScaling(profilesList)),
				statistics.evalRuntimes(dataPreprocessor.evalRuntimes(profilesList)),
				statistics.evalMiddleware(dataPreprocessor.evalMiddleware(profilesList)),
				statistics.evalFrameworks(dataPreprocessor.evalFrameworks(profilesList)),
				statistics.evalServices(dataPreprocessor.evalServices(profilesList)),
				statistics.evalExtensible(dataPreprocessor.evalExtensible(profilesList)),
				statistics.evalInfrastructures(dataPreprocessor.evalInfrastructures(profilesList)));

		GsonAdapter.createReportAsJsonFile(report, outputPath);
	}
}
