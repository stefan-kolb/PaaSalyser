package main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import models.PaasProfile;
import scanner.DirectoryScanner;
import statistics.DataPreprocessing;
import statistics.DataPreprocessingImpl;
import statistics.Statistics;
import statistics.StatisticsImpl;

public class Main {

	public static void main(String[] args) {
		List<PaasProfile> profilesList = null;
		try {
			profilesList = DirectoryScanner.scanDirectoryForJsonFiles(Paths.get("PaasProfiles"));
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

		System.out.println("---------------------------------------------------");
		DataPreprocessing dataPreprocessor = new DataPreprocessingImpl();
		System.out.println(dataPreprocessor.evalPricing(profilesList).toString());
		System.out.println("---------------------------------------------------");
		Statistics statistics = new StatisticsImpl();
		System.out.println(statistics.evalRevision(dataPreprocessor.evalRevision(profilesList)).toString());
		System.out.println(statistics.evalStatus(dataPreprocessor.evalStatus(profilesList)).toString());
		System.out.println(statistics.evalStatusSince(dataPreprocessor.evalStatusSince(profilesList)).toString());
		System.out.println(statistics.evalType(dataPreprocessor.evalType(profilesList)).toString());
		System.out.println(statistics.evalQos(dataPreprocessor.evalQos(profilesList)).toString());
		System.out.println(statistics.evalOverallCompliance(dataPreprocessor.evalCompliance()).toString());
		System.out.println(statistics.evalSpecificCompliance(dataPreprocessor.evalCompliance()).toString());
		System.out.println(statistics.evalPlatform(dataPreprocessor.evalPlatform(profilesList)).toString());
		System.out.println(statistics.evalHosting(dataPreprocessor.evalHosting(profilesList)).toString());
		System.out.println(statistics.evalPricing(dataPreprocessor.evalPricing(profilesList)).toString());
		System.out.println(statistics.evalScaling(dataPreprocessor.evalScaling(profilesList)).toString());
		System.out.println(statistics.evalRuntimes(dataPreprocessor.evalRuntimes(profilesList)).toString());
		System.out.println(statistics.evalMiddleware(dataPreprocessor.evalMiddleware(profilesList)).toString());
		System.out.println(statistics.evalFrameworks(dataPreprocessor.evalFrameworks(profilesList)).toString());
		System.out.println(statistics.evalServices(dataPreprocessor.evalServices(profilesList)).toString());
		System.out.println(statistics.evalExtensible(dataPreprocessor.evalExtensible(profilesList)).toString());
		System.out
				.println(statistics.evalInfrastructures(dataPreprocessor.evalInfrastructures(profilesList)).toString());
	}
}
