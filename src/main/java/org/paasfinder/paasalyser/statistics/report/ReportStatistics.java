package org.paasfinder.paasalyser.statistics.report;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.paasfinder.paasalyser.report.models.ExtensibleReport;
import org.paasfinder.paasalyser.report.models.HostingReport;
import org.paasfinder.paasalyser.report.models.InfrastructuresReport;
import org.paasfinder.paasalyser.report.models.PlatformReport;
import org.paasfinder.paasalyser.report.models.PricingReport;
import org.paasfinder.paasalyser.report.models.QualitativeData;
import org.paasfinder.paasalyser.report.models.RevisionReport;
import org.paasfinder.paasalyser.report.models.RuntimesReport;
import org.paasfinder.paasalyser.report.models.ScalingReport;
import org.paasfinder.paasalyser.report.models.ServicesReport;
import org.paasfinder.paasalyser.report.models.StatusReport;
import org.paasfinder.paasalyser.report.models.TypeReport;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultDouble;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportStatistics {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(ReportStatistics.class);

	private ReportPreprocessing dataPreProcessing;

	private int profilesCount;
	private int eolProfiles;
	private int invalidProfilesCount;
	private RevisionReport revision;
	private StatusReport status;
	private TypeReport type;
	private PlatformReport platform;
	private HostingReport hosting;
	private PricingReport pricing;
	private ScalingReport scaling;
	private RuntimesReport runtimes;
	private ServicesReport services;
	private ExtensibleReport extensible;
	private InfrastructuresReport infrastructures;

	/**
	 * Generates statistics out of preprocessed data.
	 * 
	 * @param reportPreprocessing
	 *            data to generate statistics of.
	 * @throws IllegalStateException
	 *             if input parameters is null.
	 */
	public ReportStatistics(ReportPreprocessing reportPreprocessing) throws IllegalStateException {
		if (reportPreprocessing == null) {
			return;
		}

		this.dataPreProcessing = reportPreprocessing;

		// Evaluate Report
		profilesCount = reportPreprocessing.getProfiles().size();
		// logger.info("eolProfiles");
		eolProfiles = (int) reportPreprocessing.getStatusData().getEol();
		invalidProfilesCount = reportPreprocessing.getInvalidProfilesCount();
		// logger.info("evalRevision");
		evalRevision();
		// logger.info("evalStatus");
		evalStatus();
		// logger.info("evalType");
		evalType();
		// logger.info("evalPlatform");
		evalPlatform();
		// logger.info("evalHosting");
		evalHosting();
		// logger.info("evalPricing");
		evalPricing();
		// logger.info("evalScaling");
		evalScaling();
		// logger.info("evalRuntimes");
		evalRuntimes();
		// logger.info("evalServices");
		evalServices();
		// logger.info("evalExtensible");
		evalExtensible();
		// logger.info("evalInfrastructures");
		evalInfrastructures();
	}

	/**
	 * Generates empty statistics.
	 */
	public ReportStatistics() {
		super();
	}

	public int getProfilesCount() {
		return profilesCount;
	}

	public int getEolProfilesCount() {
		return eolProfiles;
	}

	public int getInvalidProfilesCount() {
		return invalidProfilesCount;
	}

	public RevisionReport getRevision() {
		return revision;
	}

	public StatusReport getStatus() {
		return status;
	}

	public TypeReport getType() {
		return type;
	}

	public PlatformReport getPlatform() {
		return platform;
	}

	public HostingReport getHosting() {
		return hosting;
	}

	public PricingReport getPricing() {
		return pricing;
	}

	public ScalingReport getScaling() {
		return scaling;
	}

	public RuntimesReport getRuntimes() {
		return runtimes;
	}

	public ServicesReport getServices() {
		return services;
	}

	public ExtensibleReport getExtensible() {
		return extensible;
	}

	public InfrastructuresReport getInfrastructures() {
		return infrastructures;
	}

	private void evalRevision() {
		revision = new RevisionReport(new QualitativeData(dataPreProcessing.getRevisionData().getRevisions()),
				getMinTen(dataPreProcessing.getRevisionData().getRevisions()),
				getTopTen(dataPreProcessing.getRevisionData().getRevisions()));
	}

	private void evalStatus() {
		double[] percentages = new double[3];
		percentages[0] = calcPercent(dataPreProcessing.getStatusData().getAlpha(), profilesCount);
		percentages[1] = calcPercent(dataPreProcessing.getStatusData().getBeta(), profilesCount);
		percentages[2] = calcPercent(dataPreProcessing.getStatusData().getProduction(), profilesCount);

		status = new StatusReport(percentages, new QualitativeData(dataPreProcessing.getStatusData().getStatusSince()),
				getTopTen(dataPreProcessing.getStatusData().getStatusSince()),
				getMinTen(dataPreProcessing.getStatusData().getStatusSince()));
	}

	private void evalType() {
		double[] percentages = new double[3];
		percentages[0] = calcPercent(dataPreProcessing.getTypeData().getSaasCentric(), profilesCount);
		percentages[1] = calcPercent(dataPreProcessing.getTypeData().getGeneric(), profilesCount);
		percentages[2] = calcPercent(dataPreProcessing.getTypeData().getIaasCentric(), profilesCount);

		type = new TypeReport(percentages);
	}

	private void evalPlatform() {
		List<SimpleResultLong> platforms = dataPreProcessing.getPlatformData().getPlatforms().entrySet().stream()
				.map(entry -> new SimpleResultLong(entry.getKey(), entry.getValue()))
				.collect(Collectors.toCollection(ArrayList::new));

		platform = new PlatformReport(
				calcPercent(dataPreProcessing.getPlatformData().getplatformProfiles(), profilesCount),
				getTopTen(platforms));
	}

	private void evalHosting() {
		double[] percentages = new double[3];
		percentages[0] = calcPercent(dataPreProcessing.getHostingData().getPrivate(), profilesCount);
		percentages[1] = calcPercent(dataPreProcessing.getHostingData().getPublic(), profilesCount);
		percentages[2] = calcPercent(dataPreProcessing.getHostingData().getVirtualPrivate(), profilesCount);

		hosting = new HostingReport(percentages);
	}

	private void evalPricing() {
		List<SimpleResultLong> numberOfModelsPerProfile = new ArrayList<>(5);
		numberOfModelsPerProfile
				.add(new SimpleResultLong("Zero Models", dataPreProcessing.getPricingData().getZeroModels()));
		numberOfModelsPerProfile
				.add(new SimpleResultLong("One Models", dataPreProcessing.getPricingData().getOneModel()));
		numberOfModelsPerProfile
				.add(new SimpleResultLong("Two Models", dataPreProcessing.getPricingData().getTwoModels()));
		numberOfModelsPerProfile
				.add(new SimpleResultLong("Three Models", dataPreProcessing.getPricingData().getThreeModels()));
		numberOfModelsPerProfile
				.add(new SimpleResultLong("Four Models", dataPreProcessing.getPricingData().getFourModels()));

		List<SimpleResultLong> models = new ArrayList<>(5);
		models.add(new SimpleResultLong("Free", dataPreProcessing.getPricingData().getFreeModel()));
		models.add(new SimpleResultLong("Fixed", dataPreProcessing.getPricingData().getFixedModel()));
		models.add(new SimpleResultLong("Metered", dataPreProcessing.getPricingData().getMeteredModel()));
		models.add(new SimpleResultLong("Hybrid", dataPreProcessing.getPricingData().getHybridModel()));
		models.add(new SimpleResultLong("Empty", dataPreProcessing.getPricingData().getEmptyModel()));

		List<SimpleResultLong> periods = new ArrayList<>(4);
		periods.add(new SimpleResultLong("Daily", dataPreProcessing.getPricingData().getDailyPeriod()));
		periods.add(new SimpleResultLong("Monthly", dataPreProcessing.getPricingData().getMonthlyPeriod()));
		periods.add(new SimpleResultLong("Anually", dataPreProcessing.getPricingData().getAnnuallyPariod()));
		periods.add(new SimpleResultLong("Empty", dataPreProcessing.getPricingData().getEmptyPeriod()));

		pricing = new PricingReport(getTopTen(numberOfModelsPerProfile), getTopTen(models), getTopTen(periods));
	}

	private void evalScaling() {
		double[] percentages = new double[3];
		percentages[0] = calcPercent(dataPreProcessing.getScalingData().getVertical(), profilesCount);
		percentages[1] = calcPercent(dataPreProcessing.getScalingData().getHorizontal(), profilesCount);
		percentages[2] = calcPercent(dataPreProcessing.getScalingData().getAuto(), profilesCount);

		scaling = new ScalingReport(percentages);
	}

	private void evalRuntimes() {
		long languageSpecific = dataPreProcessing.getRuntimesData().getNumberPerProfile().entrySet().stream()
				.filter(entry -> entry.getValue() == 1).collect(Collectors.summingLong(entry -> 1));
		long polyglot = dataPreProcessing.getRuntimesData().getNumberPerProfile().entrySet().stream()
				.filter(entry -> entry.getValue() > 1).collect(Collectors.summingLong(entry -> 1));

		List<SimpleResultDouble> runtimesShare = dataPreProcessing.getRuntimesData().getRuntimes().entrySet().stream()
				.map(entry -> new SimpleResultDouble(entry.getKey(), calcPercent(entry.getValue(), profilesCount)))
				.collect(Collectors.toCollection(ArrayList::new));
		runtimesShare.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

		// // Summarize all elements after the 9th and put it back into the List
		// if (runtimesShare.size() > 8) {
		// SimpleResultDouble otherRuntimes = new SimpleResultDouble("Others",
		// runtimesShare.subList(9, runtimesShare.size()).stream()
		// .collect(Collectors.summingDouble(entry -> entry.getValue())));
		// runtimesShare = runtimesShare.subList(0, 9);
		// runtimesShare.add(otherRuntimes);
		// }

		runtimes = new RuntimesReport(languageSpecific, polyglot,
				new QualitativeData(dataPreProcessing.getRuntimesData().getNumberPerProfile()),
				getTopTen(dataPreProcessing.getRuntimesData().getNumberPerProfile()), runtimesShare);
	}

	private void evalServices() {
		double profilesWithNativeServices = calcPercent(
				dataPreProcessing.getServicesData().getProfilesWithNativeServices().size(), profilesCount);

		services = new ServicesReport(profilesWithNativeServices,
				getTopTen(dataPreProcessing.getServicesData().getProfilesWithNativeServices()),
				getTopTen(dataPreProcessing.getServicesData().getNativeServices()),
				getTopTen(dataPreProcessing.getServicesData().getTypesOfNativeServices().entrySet().stream()
						.filter(entry -> !entry.getKey().equals("empty"))
						.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()))));
	}

	private void evalExtensible() {
		double extensibleCount = calcPercent(dataPreProcessing.getExtensibleData().getTrue(), profilesCount);

		extensible = new ExtensibleReport(extensibleCount);
	}

	private void evalInfrastructures() {
		List<SimpleResultDouble> percentOfProfilesPerContinent = dataPreProcessing.getInfrastructuresData()
				.getProfileContinents().entrySet().stream()
				.map(entry -> new SimpleResultDouble(entry.getKey(),
						calcPercent(entry.getValue(),
								// public and virtual_private are hosted in data
								// centers, private is not
								dataPreProcessing.getHostingData().getPublic()
										+ dataPreProcessing.getHostingData().getVirtualPrivate())))
				.collect(Collectors.toCollection(ArrayList::new));

		infrastructures = new InfrastructuresReport(
				getTopTen(dataPreProcessing.getInfrastructuresData().getInfrastructuresPerProfile()),
				new QualitativeData(dataPreProcessing.getInfrastructuresData().getInfrastructuresPerProfile()),
				sortDescendingDouble(percentOfProfilesPerContinent),
				getTopTen(dataPreProcessing.getInfrastructuresData().getContinent()),
				getTopTen(dataPreProcessing.getInfrastructuresData().getCountry()),
				getTopTen(dataPreProcessing.getInfrastructuresData().getRegion()),
				getTopTen(dataPreProcessing.getInfrastructuresData().getProvider()));
	}

	// Private Methods that are used to calculate several results

	private List<SimpleResultLong> getTopTen(Map<String, Long> list) {
		List<SimpleResultLong> results = list.entrySet().stream().map(entry -> {
			return new SimpleResultLong(entry.getKey(), entry.getValue());
		}).collect(Collectors.toCollection(ArrayList::new));
		// Sort descending
		results.sort((x, y) -> Long.compare(y.getValue(), x.getValue()));
		// Return the Top 5 Elements
		if (list.size() >= 10) {
			return results.subList(0, 10);
		} else
			return results;
	}

	private List<SimpleResultLong> getTopTen(List<SimpleResultLong> list) {
		// Sort descending
		list.sort((x, y) -> Long.compare(y.getValue(), x.getValue()));
		// Return the Top 5 Elements
		if (list.size() >= 10) {
			return list.subList(0, 10);
		} else
			return list;
	}

	@SuppressWarnings("unused")
	private List<SimpleResultDouble> getTopTenDouble(List<SimpleResultDouble> list) {
		// Sort descending
		list.sort((x, y) -> Double.compare(y.getValue(), x.getValue()));
		// Return the Top 5 Elements
		if (list.size() >= 10) {
			return list.subList(0, 10);
		} else
			return list;
	}

	private List<SimpleResultDouble> sortDescendingDouble(List<SimpleResultDouble> list) {
		// Sort descending
		list.sort((x, y) -> Double.compare(y.getValue(), x.getValue()));
		return list;
	}

	private List<SimpleResultLong> getMinTen(Map<String, Long> list) {
		List<SimpleResultLong> results = list.entrySet().stream().map(entry -> {
			return new SimpleResultLong(entry.getKey(), entry.getValue());
		}).collect(Collectors.toCollection(ArrayList::new));
		// Sort descending
		results.sort(Comparator.comparingLong(SimpleResultLong::getValue));
		// Return the Top 5 Elements
		if (list.size() >= 10) {
			return results.subList(0, 10);
		} else
			return results;
	}

	@SuppressWarnings("unused")
	private List<SimpleResultLong> getMinFive(List<SimpleResultLong> list) {
		// Sort descending
		list.sort(Comparator.comparingLong(SimpleResultLong::getValue));
		// Return the Min 5 Elements
		if (list.size() >= 5) {
			return list.subList(0, 5);
		} else
			return list;
	}

	private double calcPercent(Number a, Number b) {
		return (a.doubleValue() / b.doubleValue()) * 100;
	}

}
