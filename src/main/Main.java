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

		DataPreprocessing statistics = new DataPreprocessingImpl();
		System.out.println(statistics.evalRevision(profilesList).toString());
		System.out.println(statistics.evalStatus(profilesList).toString());
		System.out.println(statistics.evalStatusSince(profilesList).toString());
		System.out.println(statistics.evalType(profilesList).toString());
		System.out.println(statistics.evalHosting(profilesList).toString());
		System.out.println(statistics.evalPricing(profilesList).toString());
		System.out.println(statistics.evalRuntimes(profilesList).toString());
	}
}
