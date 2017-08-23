package org.paasfinder.paasalyser;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		LOGGER.info("Starting Execution.");
		final Executionmanager executionManager = new Executionmanager();
		try {
//			executionManager.scanStateOfTheArt();
			executionManager.scanRepository();
			LOGGER.info("Shutting down properly.");
			System.exit(0);
		} catch (IOException e) {
			LOGGER.error("IOException occured while executing", e);
			System.exit(1);
		} catch (GitAPIException e) {
			LOGGER.error("GitAPIException occured while executing", e);
			System.exit(1);
		}
	}

}
