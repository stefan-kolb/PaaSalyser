package org.paasfinder.paasalyser.database;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.paasfinder.paasalyser.report.PaasReport;
import org.paasfinder.paasalyser.report.models.MetaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;

public class DatabaseConnectorImpl implements DatabaseConnector {

	private final Logger logger = LoggerFactory.getLogger(DatabaseConnectorImpl.class);

	private final Morphia morphia;
	private final Datastore datastore;

	public DatabaseConnectorImpl() {
		super();
		logger.info("Setting up Database connection...");
		morphia = new Morphia();
		morphia.mapPackage("org.paasfinder.paasalyser.report");
		morphia.mapPackage("org.paasfinder.paasalyser.report.models");

		datastore = morphia.createDatastore(new MongoClient(), "paasalyser");
		datastore.ensureIndexes();

		logger.info("Database connected");

		// logger.info("Clearing Database");
		// datastore.getDB().dropDatabase();
	}

	public void savePaasReport(PaasReport paasReport) {
		logger.info("Saving Paasprofile " + paasReport.getCommitHash());
		datastore.save(paasReport);
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

	public void deletePaasReport(String commitHash) {
		logger.info("Deleting Paasprofile " + commitHash);
		datastore.delete(datastore.createQuery(PaasReport.class).field("id").equal(commitHash));
	}

	public List<MetaInfo> getProfileAmounts() {
		List<PaasReport> relevantReports = datastore.createQuery(PaasReport.class).order("metainfos.date")
				.project("metainfos.numberOfProfiles", true).project("metainfos.numberOfEolProfiles", true)
				.project("metainfos.date", true).asList();

		return relevantReports.stream().map(elem -> {
			return elem.getMetaInfo();
		}).collect(Collectors.toCollection(LinkedList::new));
	}

	public List<MetaInfo> getRevisionAmounts() {
		List<PaasReport> relevantReports = datastore.createQuery(PaasReport.class).order("metainfos.date")
				.project("metainfos.date", true).project("metainfos.revisionReport", true).asList();

		return relevantReports.stream().map(elem -> {
			return elem.getMetaInfo();
		}).collect(Collectors.toCollection(LinkedList::new));
	}

	public List<MetaInfo> getTypeAmounts() {
		List<PaasReport> relevantReports = datastore.createQuery(PaasReport.class).order("metainfos.date")
				.project("metainfos.date", true).project("metainfos.typeReport", true).asList();

		return relevantReports.stream().map(elem -> {
			return elem.getMetaInfo();
		}).collect(Collectors.toCollection(LinkedList::new));
	}

	public List<PaasReport> getStatusAmounts() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date").project("metainfos.date", true)
				.project("businessinfos.statusReport", true).asList();
	}

	public List<PaasReport> getPricingAmounts() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date").project("metainfos.date", true)
				.project("businessinfos.pricingReport", true).asList();
	}

	public List<PaasReport> getHostingAmounts() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date").project("metainfos.date", true)
				.project("economicinfos.hostingReport", true).asList();
	}

	public List<PaasReport> getScalingAmounts() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date").project("metainfos.date", true)
				.project("economicinfos.scalingReport", true).asList();
	}

	public List<PaasReport> getRuntimesAmounts() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date").project("metainfos.date", true)
				.project("economicinfos.runtimesReport", true).asList();
	}

	public List<PaasReport> getServicesAmounts() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date").project("metainfos.date", true)
				.project("economicinfos.servicesReport", true).asList();
	}

	public List<PaasReport> getExtensibleAmounts() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date").project("metainfos.date", true)
				.project("economicinfos.extensibleReport", true).asList();
	}

	public List<PaasReport> getInfrastructuresAmounts() {
		return datastore.createQuery(PaasReport.class).order("metainfos.date").project("metainfos.date", true)
				.project("economicinfos.infrastructuresReport", true).asList();
	}

}