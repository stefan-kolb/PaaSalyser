package org.paasfinder.paasalyser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
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
						logger.error("Could not process commit " + commitProfile.getKey().getName());
					}
				}
			}
		}
	}

	private void processProfiles(Map.Entry<RevCommit, List<PaasProfile>> commitProfile) throws IOException {
		logger.info("Processing");
		DataPreprocessing dataPreprocessing = new DataPreprocessing(commitProfile.getValue());
		Statistics statistics = new Statistics(dataPreprocessing);
		Report report = new Report(statistics);
		Path path = Paths.get("Reports/" + commitProfile.getKey().getAuthorIdent().getWhen().getTime() + "_"
				+ commitProfile.getKey().getName() + ".json");
		gsonAdapter.createReportAsJsonFile(report, path);
	}

}
