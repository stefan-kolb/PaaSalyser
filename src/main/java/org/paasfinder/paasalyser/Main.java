package org.paasfinder.paasalyser;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		LOGGER.info("Starting Execution.");
		Executionmanager executionManager = null;
		try {
			executionManager = new Executionmanager();
		} catch (IOException e) {
			LOGGER.error("IOException occured while executing", e);
		}
		try {
			// executionManager.scanTestProfiles();
			executionManager.scanRepository();
			executionManager.createTimeseriesAnalysisFiles();

		} catch (IOException e) {
			LOGGER.error("IOException occured while executing", e);
			System.exit(1);
		} catch (GitAPIException e) {
			LOGGER.error("GitAPIException occured while executing", e);
			System.exit(1);
		} finally {
			LOGGER.info("Database is being closed (0 is ok): " + executionManager.shutdownDatabaseProcess());
		}
		LOGGER.info("Shutting down properly.");
	}

}
