package org.paasfinder.paasalyser.database;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.paasfinder.paasalyser.report.PaasReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;

public class DatabaseConnector {

	private final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);

	private final Morphia morphia;
	private final Datastore datastore;

	public DatabaseConnector() {
		super();
		logger.info("Setting up Database connection...");
		morphia = new Morphia();
		morphia.mapPackage("org.paasfinder.paasalyser.report");
		morphia.mapPackage("org.paasfinder.paasalyser.report.models");

		datastore = morphia.createDatastore(new MongoClient(), "paasalyser");
		datastore.ensureIndexes();

		logger.info("Database connected");

		logger.info("Clearing Database");
		datastore.getDB().dropDatabase();
	}

	public boolean savePaasProfile(PaasReport paasReport) {
		logger.info("Saving Paasprofile " + paasReport.getCommitHash());
		datastore.save(paasReport);
		return true;
	}

	public boolean contains(PaasReport paasReport) {
		logger.info("Searching for Paasprofile " + paasReport.getCommitHash());
		return datastore.find(PaasReport.class).filter("commitHash ==", paasReport.getCommitHash()).get() != null;
	}

	public boolean contains(String commitHash) {
		logger.info("Searching for Paasprofile " + commitHash);
		return datastore.find(PaasReport.class).filter("commitHash ==", commitHash).get() != null;
	}

	public List<PaasReport> getAllPaasReportsFromDatabase() {
		logger.info("Querying all Paasprofiles");
		return datastore.createQuery(PaasReport.class).asList();
	}

	public PaasReport getPaasReport(String commitHash) {
		logger.info("Querying Paasprofile " + commitHash);
		return datastore.createQuery(PaasReport.class).field("id").equal(commitHash).get();
	}

	public void deletePaasReport(String commitHash) {
		logger.info("Deleting Paasprofile " + commitHash);
		datastore.delete(datastore.createQuery(PaasReport.class).field("id").equal(commitHash));
	}

	public List<PaasReport> getNumberOfProfilesAsList() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date")
				.project("metainfos.numberOfProfiles", true).project("metainfos.date", true).asList();
	}

}
