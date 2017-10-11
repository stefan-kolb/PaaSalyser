package org.paasfinder.paasalyser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

	private Process mongoDB = null;

	private GsonAdapter gsonAdapter;

	private final String gitRemotePath = "git@github.com:stefan-kolb/paas-profiles.git";
	private final String pathOfProfilesRepository = "paas-profiles";
	private final String pathOfReports = "Reports";

	public Executionmanager() throws IOException {
		super();
		logger.info("Starting MongoDB");

		mongoDB = startMongoDB();

		gsonAdapter = new GsonAdapter();
	}

	public int shutdownDatabaseProcess() {
		mongoDB.destroyForcibly();
		return mongoDB.exitValue();
	}

	private Process startMongoDB() throws IOException {
		String[] commands = { "cmd", "/c", "start runMongoDB" };
		// Run the script in the project's directory to start MongoDB
		File projectDirectory = Paths.get(".").toFile();

		return new ProcessBuilder(commands).directory(projectDirectory).start();
	}

	public void execute() throws GitAPIException, IOException {
		try (DatabaseConnector database = new DatabaseConnectorImpl()) {
			scanRepository(database);
			createStateOfTheArtReport(database);
			createTimeseriesAnalysisFiles(database);
		}
	}

	private void createStateOfTheArtReport(DatabaseConnector database) throws IOException, GitAPIException {
		logger.info("Retrieving State of the Art");
		Path outputPath = Paths.get(pathOfReports + "/" + "State-of-the-art" + ".json");
		Files.deleteIfExists(outputPath);
		gsonAdapter.createReportAsJsonFile(database.getStateOfTheArtReport(), outputPath);
		logger.info("Successfully scanned State of the Art");
	}

	/**
	 * Creates CSV files with the timeseries data of the reports in the database
	 */
	private void createTimeseriesAnalysisFiles(DatabaseConnector database) {
		TimeseriesStatistics timeseries = new TimeseriesStatistics();
		TimeseriesCSVPrinter printer = new TimeseriesCSVPrinter(pathOfReports);

		printer.printToCSVFile("ProfileAmounts", timeseries.evalProfileAmounts(database.getProfilesData()));
		printer.printToCSVFile("Revision", timeseries.evalRevision(database.getRevisionsData()));
		printer.printToCSVFile("Type", timeseries.evalType(database.getTypesData()));
		printer.printToCSVFile("Platform", timeseries.evalPlatform(database.getPlatformData()));
		printer.printToCSVFile("Status", timeseries.evalStatus(database.getStatusData()));
		printer.printToCSVFile("Pricing", timeseries.evalPricing(database.getPricingsData()));
		printer.printToCSVFile("Hosting", timeseries.evalHosting(database.getHostingsData()));
		printer.printToCSVFile("Scaling", timeseries.evalScaling(database.getScalingsData()));
		printer.printToCSVFile("Status", timeseries.evalStatus(database.getStatusData()));
		printer.printToCSVFile("Runtimes Profiles", timeseries.evalRuntimesProfiles(database.getRuntimesData()));
		printer.printToCSVFile("Runtimes Share", timeseries.evalRuntimesShare(database.getRuntimesData()));
		printer.printToCSVFile("Services", timeseries.evalServices(database.getServicesData()));
		printer.printToCSVFile("Extensible", timeseries.evalExtensible(database.getExtensibleData()));
		printer.printToCSVFile("InfrastructureProfiles", timeseries.evalInfraStructureProfiles(database.getInfrastructuresData()));
		printer.printToCSVFile("InfrastructureContinents",
				timeseries.evalInfraStructureContinents(database.getInfrastructuresData()));
	}

	/**
	 * Scans the paas-profiles repository for commits where profiles have been
	 * changed, generates a report out of the profiles and stores it in the
	 * database.
	 * 
	 * @throws IOException
	 *             An error occurred while setting up {@link RepositorySniffer}
	 *             or an error occurred during scanning the repository
	 * @throws GitAPIException
	 *             An error occurred while setting up {@link RepositorySniffer}
	 *             or an error occurred during scanning the repository
	 */
	private void scanRepository(DatabaseConnector database) throws GitAPIException, IOException {
		try (RepositorySniffer sniffer = new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository,
				database)) {

			Map<RevCommit, List<PaasProfile>> commitsToProcess = sniffer.sniffRepository();
			logger.info("Number of commits to process is: " + commitsToProcess.size());

			for (Map.Entry<RevCommit, List<PaasProfile>> commit : commitsToProcess.entrySet()) {
				try {
					database.savePaasReport(processCommitProfiles(commit));
				} catch (IOException e) {
					logger.error("IOException occurred - Could not process commit " + commit.getKey().getName() + " | "
							+ e.getMessage());
				} catch (RuntimeException e) {
					logger.error("RuntimeException occurred - Could not process commit " + commit.getKey().getName()
							+ " | " + e.getMessage());
				}
			}
		}
	}

	private PaasReport processCommitProfiles(Map.Entry<RevCommit, List<PaasProfile>> commitProfile)
			throws IOException, RuntimeException {
		logger.info("Processing");

		Instant instant = commitProfile.getKey().getAuthorIdent().getWhen().toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		ReportPreprocessing reportPreprocessing;
		ReportStatistics reportStatistics;
		try {
			reportPreprocessing = new ReportPreprocessing(localDate, commitProfile.getValue());
			reportStatistics = new ReportStatistics(reportPreprocessing);
		} catch (IllegalStateException e) {
			throw new RuntimeException(
					"IllegalStateException occurred while processing profiles of: " + commitProfile.getKey().getName(),
					e);
		}

		logger.info("Generating Report");
		PaasReport report;
		try {

			report = new PaasReport(commitProfile.getKey().getName(), localDate, reportStatistics);
		} catch (IllegalStateException e) {
			logger.warn("Statistics was null. An empty report is being generated");
			report = new PaasReport();
		}
		return report;
	}

}
