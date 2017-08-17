package org.paasfinder.paasalyser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.paasfinder.paasalyser.gsonutility.GsonAdapter;
import org.paasfinder.paasalyser.profile.PaasProfile;
import org.paasfinder.paasalyser.report.Report;
import org.paasfinder.paasalyser.revision.RepositorySniffer;
import org.paasfinder.paasalyser.statistics.DataPreprocessing;
import org.paasfinder.paasalyser.statistics.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Executionmanager {

	private final Logger logger = LoggerFactory.getLogger(Executionmanager.class);

	private GsonAdapter gsonAdapter;
	private RepositorySniffer repoSniffer;

	private final String gitRemotePath = "git@github.com:stefan-kolb/paas-profiles.git";
	private final String pathOfProfilesRepository = "paas-profiles";
	private final Path directoryToScan = Paths.get("paas-profiles/profiles");

	public Executionmanager() {
		super();
		gsonAdapter = new GsonAdapter();
		repoSniffer = new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository);
	}

	public void scanStateOfTheArt() throws IOException, GitAPIException {
		logger.info("Scanning state of the art");

		Git git = repoSniffer.initializeOrResetRepository();
		String currentCommitName = git.log().call().iterator().next().getName();
		logger.info("Current commit is: " + currentCommitName);
		Path outputPath = Paths.get("Reports/PaaSReport_" + currentCommitName + ".json");

		if (Files.exists(outputPath)) {
			logger.info("Report already existing");
			return;
		}

		evaluateDirectory(outputPath);
		logger.info("Finished scanning state of the art");
	}

	private void evaluateDirectory(Path outputPath) throws IOException {
		List<PaasProfile> profilesList = new ArrayList<PaasProfile>();

		try {
			profilesList = gsonAdapter.scanDirectoryForJsonFiles(directoryToScan);
		} catch (IOException e) {
			logger.error(directoryToScan + " is not a valid directory", e);
		}

		generateReport(profilesList, outputPath);
	}

	private void generateReport(List<PaasProfile> profilesList, Path outputPath) throws IOException {
		logger.info("Preprocessing Data");
		DataPreprocessing dataPreprocessing = new DataPreprocessing(profilesList);

		logger.info("Generating Statistics");
		Statistics statistics = new Statistics(dataPreprocessing);

		logger.info("Creating Report");
		Report report = new Report(statistics);

		logger.info("Writing Report to File");
		gsonAdapter.createReportAsJsonFile(report, outputPath);
	}

}
