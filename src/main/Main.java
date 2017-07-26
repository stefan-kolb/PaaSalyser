package main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import gsonUtility.GsonAdapter;
import profile.PaasProfile;
import report.Report;
import statistics.DataPreprocessing;
import statistics.StatisticsImplWithModels;

public class Main {

    static GsonAdapter gsonAdapter = new GsonAdapter();

    public static void main(String[] args) {
	System.out.println("Starting Execution.");

	Path directoryToScan = Paths.get("PaasProfiles");
	Path outputPath = Paths.get("Reports/PaaSReport_"
		+ LocalDateTime.now().format(
			DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss"))
		+ ".json");
	try {

	    // This is where the magic happens.
	    evaluateDirectory(directoryToScan, outputPath);

	    System.out.println();
	    System.out.println("Finished creating: " + outputPath);
	    System.exit(0);
	} catch (IOException e) {
	    System.out.println();
	    System.out.println("A Problem occured while scanning: "
		    + e.getMessage());
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    private static void evaluateDirectory(Path directory, Path outputPath)
	    throws IOException {
	List<PaasProfile> profilesList = new ArrayList<PaasProfile>();

	profilesList = gsonAdapter.scanDirectoryForJsonFiles(Paths
		.get("PaasProfiles"));
	for (PaasProfile profile : profilesList) {
	    if (profile.isFailed() == true) {
		throw new IOException("Failed to scan profiles at: "
			+ profile.getName());
	    }
	}

	generateReport(profilesList, outputPath);
    }

    private static void generateReport(List<PaasProfile> profilesList,
	    Path outputPath) throws IOException {
	System.out.println();
	System.out.println("---Preprocessing---");
	DataPreprocessing dataPreprocessing = new DataPreprocessing(
		profilesList);

	System.out.println();
	System.out.println("---Statistics---");
	StatisticsImplWithModels statistics = new StatisticsImplWithModels(
		dataPreprocessing);

	System.out.println();
	System.out.println("---Creating Report---");
	Report report = new Report(statistics);

	System.out.println();
	System.out.println("---Writing to File---");
	gsonAdapter.createReportAsJsonFile(report, outputPath);
    }
}
