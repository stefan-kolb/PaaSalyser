package org.paasfinder.paasalyser;

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
import org.paasfinder.paasalyser.gsonutility.GsonAdapter;
import org.paasfinder.paasalyser.profile.PaasProfile;
import org.paasfinder.paasalyser.report.PaasReport;
import org.paasfinder.paasalyser.revision.RepositorySniffer;
import org.paasfinder.paasalyser.statistics.report.ReportPreprocessing;
import org.paasfinder.paasalyser.statistics.report.ReportStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Executionmanager {

	private final Logger logger = LoggerFactory.getLogger(Executionmanager.class);

	private GsonAdapter gsonAdapter;

	private final String gitRemotePath = "git@github.com:stefan-kolb/paas-profiles.git";
	private final String pathOfProfilesRepository = "paas-profiles";

	public Executionmanager() {
		super();
		gsonAdapter = new GsonAdapter();
	}

	public void scanStateOfTheArt() throws IOException, GitAPIException {
		try (RepositorySniffer sniffer = new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository)) {
			logger.info("Scanning State of the Art");
			processProfiles(sniffer.getStateOfTheArt());
		}
		logger.info("Successfully scanned State of the Art");
	}

	public void scanRepository() throws IOException, GitAPIException {
		try (RepositorySniffer sniffer = new RepositorySniffer(gsonAdapter, gitRemotePath, pathOfProfilesRepository)) {

			Map<RevCommit, List<PaasProfile>> profilesOfCommits = sniffer.sniffRepository();
			logger.info("Number of successfully scanned commits is: " + profilesOfCommits.size());

			for (Map.Entry<RevCommit, List<PaasProfile>> commitProfile : profilesOfCommits.entrySet()) {
				Path path = Paths.get("Reports/" + commitProfile.getKey().getAuthorIdent().getWhen().getTime() + "_"
						+ commitProfile.getKey().getName() + ".json");
				if (!Files.exists(path)) {
					try {
						processProfiles(commitProfile);
						logger.info("Succesfully scanned " + commitProfile.getKey().getName());
					} catch (IOException e) {
						logger.error("IOException occurred - Could not process commit "
								+ commitProfile.getKey().getName() + " | " + e.getMessage());
					} catch (RuntimeException e) {
						logger.error("RuntimeException occurred - Could not process commit "
								+ commitProfile.getKey().getName() + " | " + e.getMessage());
					}
				} else {
					logger.info(path + " already exists.");
				}
			}
		}
	}

	private void processProfiles(Map.Entry<RevCommit, List<PaasProfile>> commitProfile)
			throws IOException, RuntimeException {
		logger.info("Processing");
		ReportPreprocessing dataPreprocessing;
		ReportStatistics statistics;
		try {
			dataPreprocessing = new ReportPreprocessing(commitProfile.getValue());
			statistics = new ReportStatistics(dataPreprocessing);
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
			report = new PaasReport(localDate, statistics);
		} catch (IllegalStateException e) {
			logger.warn("Statistics was null. An empty report is being generated");
			report = new PaasReport();
		}

		Path path = Paths.get("Reports/" + commitProfile.getKey().getAuthorIdent().getWhen().getTime() + "_"
				+ commitProfile.getKey().getName() + ".json");
		try {
			gsonAdapter.createReportAsJsonFile(report, path);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("IllegalArgumentException occurred while processing profiles of: "
					+ commitProfile.getKey().getName() + " | " + e.getMessage());
		}
	}

}
