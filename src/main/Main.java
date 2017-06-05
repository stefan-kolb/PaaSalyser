package main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import models.PaasProfile;
import scanner.DirectoryScanner;
import statistics.DataPreprocessing;
import statistics.DataPreprocessingImpl;

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
					System.out.println("Profile - " + profile.toString());
				}
			});
		} catch (IOException e) {
			System.out.println("Failed");
		}

		DataPreprocessing dataPreprocessor = new DataPreprocessingImpl();
		System.out.println(dataPreprocessor.evalRevision(profilesList).toString());
		System.out.println(dataPreprocessor.evalStatus(profilesList).toString());
		System.out.println(dataPreprocessor.evalStatusSince(profilesList).toString());
		System.out.println(dataPreprocessor.evalType(profilesList).toString());
		System.out.println(dataPreprocessor.evalHosting(profilesList).toString());
		System.out.println(dataPreprocessor.evalPricing(profilesList).toString());
		System.out.println(dataPreprocessor.evalRuntimes(profilesList).toString());
	}
}
