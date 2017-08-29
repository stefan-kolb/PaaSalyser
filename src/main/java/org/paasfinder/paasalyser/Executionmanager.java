package org.paasfinder.paasalyser;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.paasfinder.paasalyser.database.DatabaseConnector;
import org.paasfinder.paasalyser.database.DatabaseConnectorImpl;
import org.paasfinder.paasalyser.gsonutility.GsonAdapter;
import org.paasfinder.paasalyser.profile.PaasProfile;
import org.paasfinder.paasalyser.report.PaasReport;
import org.paasfinder.paasalyser.revision.RepositorySniffer;
import org.paasfinder.paasalyser.statistics.report.ReportPreprocessing;
import org.paasfinder.paasalyser.statistics.report.ReportStatistics;
import org.paasfinder.paasalyser.statistics.timeseries.TimeseriesCSVPrinter;
import org.paasfinder.paasalyser.statistics.timeseries.TimeseriesStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Executionmanager {

	private final Logger logger = LoggerFactory.getLogger(Executionmanager.class);

	Process mongoDB = null;

	private GsonAdapter gsonAdapter;
	private DatabaseConnector database;

	private final String gitRemotePath = "git@github.com:stefan-kolb/paas-profiles.git";
	private final String pathOfProfilesRepository = "paas-profiles";
	private final String pathOfTimeseriesReports = "Timeseries Reports";

	public Executionmanager() throws IOException {
		super();
		logger.info("Starting MongoDB");
		String command = "cmd /c start runMongoDB";
		mongoDB = Runtime.getRuntime().exec(command);

		gsonAdapter = new GsonAdapter();
		database = new DatabaseConnectorImpl();
	}

	public void close() {
		mongoDB.destroy();
	}

	public void scanStateOfTheArt() throws IOException, GitAPIException {
		try (RepositorySniffer sniffer = new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository,
				database)) {
			logger.info("Scanning State of the Art");
			if (database.contains(sniffer.getStateOfTheArtCommitName())) {
				logger.info("Commit already in database");
				return;
			}
			processCommitProfiles(sniffer.getStateOfTheArt());
		}
		logger.info("Successfully scanned State of the Art");
	}

	public void scanTestProfiles() throws IllegalStateException, IOException, GitAPIException {
		try (RepositorySniffer sniffer = new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository,
				database)) {
			logger.info("Scanning Test Profiles");
			processTestProfiles(gsonAdapter.scanDirectoryForJsonFiles(Paths.get("test-profiles")));
		}
		logger.info("Successfully scanned Test Profiles");
	}

	public void createStatisticalAnalysis() {
		TimeseriesStatistics timeseries = new TimeseriesStatistics();
		TimeseriesCSVPrinter printer = new TimeseriesCSVPrinter(pathOfTimeseriesReports);

		printer.printToCSVFile("ProfileAmounts", timeseries.evalProfileAmounts(database.getProfileAmounts()));
	}

	public void scanRepository() throws IOException, GitAPIException {
		try (RepositorySniffer sniffer = new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository,
				database)) {

			Map<RevCommit, List<PaasProfile>> profilesOfCommits = sniffer.sniffRepository();
			logger.info("Number of commits to process is: " + profilesOfCommits.size());

			for (Map.Entry<RevCommit, List<PaasProfile>> commitProfile : profilesOfCommits.entrySet()) {
				if (!database.contains(commitProfile.getKey().getName())) {
					try {
						database.savePaasReport(processCommitProfiles(commitProfile));
					} catch (IOException e) {
						logger.error("IOException occurred - Could not process commit "
								+ commitProfile.getKey().getName() + " | " + e.getMessage());
					} catch (RuntimeException e) {
						logger.error("RuntimeException occurred - Could not process commit "
								+ commitProfile.getKey().getName() + " | " + e.getMessage());
					}
				} else {
					logger.info("Commit already in database");
				}
			}
		}
	}

	private PaasReport processCommitProfiles(Map.Entry<RevCommit, List<PaasProfile>> commitProfile)
			throws IOException, RuntimeException {
		logger.info("Processing");
		ReportPreprocessing reportPreprocessing;
		ReportStatistics reportStatistics;
		try {
			reportPreprocessing = new ReportPreprocessing(commitProfile.getValue());
			System.out.println(reportPreprocessing.toString());
			reportStatistics = new ReportStatistics(reportPreprocessing);
		} catch (IllegalStateException e) {
			throw new RuntimeException(
					"IllegalStateException occurred while processing profiles of: " + commitProfile.getKey().getName(),
					e);
		}

		logger.info("Generating Report");
		PaasReport report;
		try {
			Instant instant = commitProfile.getKey().getAuthorIdent().getWhen().toInstant();
			LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
			report = new PaasReport(commitProfile.getKey().getName(), localDate, reportStatistics);
		} catch (IllegalStateException e) {
			logger.warn("Statistics was null. An empty report is being generated");
			report = new PaasReport();
		}
		return report;
	}

	private void processTestProfiles(List<PaasProfile> commitProfiles) throws IOException, RuntimeException {
		logger.info("Processing");
		ReportPreprocessing reportPreprocessing;
		ReportStatistics reportStatistics;
		try {
			reportPreprocessing = new ReportPreprocessing(commitProfiles);
			System.out.println(reportPreprocessing.toString());
			System.out.println("--------------------------------------------");
			reportStatistics = new ReportStatistics(reportPreprocessing);
			System.out.println(reportStatistics.toString());
		} catch (IllegalStateException e) {
			throw new RuntimeException("IllegalStateException occurred while processing test profiles", e);
		}
	}

}
