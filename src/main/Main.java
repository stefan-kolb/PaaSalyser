package main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import models.PaasProfile;
import scanner.DirectoryScanner;
import statistics.Statistics;

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
		System.out.println(Statistics.evalRevision(profilesList).toString());
		System.out.println(Statistics.evalStatus(profilesList).toString());
		System.out.println(Statistics.evalStatusSince(profilesList).toString());
		System.out.println(Statistics.evalType(profilesList).toString());
		System.out.println(Statistics.evalHosting(profilesList).toString());
		System.out.println(Statistics.evalPricing(profilesList).toString());
	}
}
