package main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import models.PaasProfile;
import scanner.DirectoryScanner;
import statistics.EvaluationResult;
import statistics.Statistics;

public class Main {

	public static void main(String[] args) {
		List<PaasProfile> profilesList = null;
		List<EvaluationResult> resultList = null;
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
		resultList = Statistics.evalStatus(profilesList);
		String statusResult1 = "\n" + resultList.get(0).getName() + " - " + resultList.get(0).getNumber();
		String statusResult2 = "\n" + resultList.get(1).getName() + " - " + resultList.get(1).getNumber();
		String statusResult3 = "\n" + resultList.get(2).getName() + " - " + resultList.get(2).getNumber();
		System.out.println("Results for Status are: " + statusResult1 + statusResult2 + statusResult3);

		resultList = Statistics.evalType(profilesList);
		String typeResult1 = "\n" + resultList.get(0).getName() + " - " + resultList.get(0).getNumber();
		String typeResult2 = "\n" + resultList.get(1).getName() + " - " + resultList.get(1).getNumber();
		String typeResult3 = "\n" + resultList.get(2).getName() + " - " + resultList.get(2).getNumber();
		System.out.println("Results for Status are: " + typeResult1 + typeResult2 + typeResult3);
	}
}
