package main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import gsonUtility.GsonAdapter;
import models.PaasProfile;
import models.Report;
import statistics.DataPreprocessing;
import statistics.DataPreprocessingImpl;
import statistics.Statistics;
import statistics.StatisticsImpl;

public class Main {

	public static void main(String[] args) {
		List<PaasProfile> profilesList = null;
		try {
			profilesList = GsonAdapter.scanDirectoryForJsonFiles(Paths.get("PaasProfiles"));
			profilesList.forEach(profile -> {
				if (profile.isFailed() == true) {
					System.out.println("Something went wrong.");
					System.exit(0);
				} else {
					System.out.println(profile.toString());
				}
			});
		} catch (IOException e) {
			System.out.println("Failed");
		}

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

		// Get the file reference
		Path path = Paths.get("D:/Dokumente/Studium/paasalyser/Reports/"
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")) + ".json");
		
		try {
			GsonAdapter.createReportAsJsonFile(report, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
