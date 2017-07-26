package statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import report.models.ExtensibleReport;
import report.models.FrameworksReport;
import report.models.HostingReport;
import report.models.InfrastructuresReport;
import report.models.MiddlewareReport;
import report.models.PlatformReport;
import report.models.PricingReport;
import report.models.QualitativeData;
import report.models.RevisionReport;
import report.models.RuntimesReport;
import report.models.ScalingReport;
import report.models.ServicesReport;
import report.models.StatusReport;
import report.models.TypeReport;
import statistics.models.SimpleResultDouble;
import statistics.models.SimpleResultLong;

public class StatisticsImplWithModels {

    private DataPreprocessing dataPreProcessing;

    private int profilesCount;
    private int eolProfiles;
    private RevisionReport revision;
    private StatusReport status;
    private TypeReport type;
    private PlatformReport platform;
    private HostingReport hosting;
    private PricingReport pricing;
    private ScalingReport scaling;
    private RuntimesReport runtimes;
    private MiddlewareReport middleware;
    private FrameworksReport frameworks;
    private ServicesReport services;
    private ExtensibleReport extensible;
    private InfrastructuresReport infrastructures;

    public StatisticsImplWithModels(DataPreprocessing dataPreProcessing) {
	this.dataPreProcessing = dataPreProcessing;

	// Evaluate Report
	System.out.println("profilesCount");
	profilesCount = dataPreProcessing.getProfiles().size();
	System.out.println("eolProfiles");
	eolProfiles = (int) dataPreProcessing.getStatusData().getEol();
	System.out.println("evalRevision");
	evalRevision();
	System.out.println("evalStatus");
	evalStatus();
	System.out.println("evalType");
	evalType();
	System.out.println("evalPlatform");
	evalPlatform();
	System.out.println("evalHosting");
	evalHosting();
	System.out.println("evalPricing");
	evalPricing();
	System.out.println("evalScaling");
	evalScaling();
	System.out.println("evalRuntimes");
	evalRuntimes();
	System.out.println("evalMiddleware");
	evalMiddleware();
	System.out.println("evalFrameworks");
	evalFrameworks();
	System.out.println("evalServices");
	evalServices();
	System.out.println("evalExtensible");
	evalExtensible();
	System.out.println("evalInfrastructures");
	evalInfrastructures();
    }

    public int getProfilesCount() {
	return profilesCount;
    }

    public int getEolProfilesCount() {
	return eolProfiles;
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

    public MiddlewareReport getMiddleware() {
	return middleware;
    }

    public FrameworksReport getFrameworks() {
	return frameworks;
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
	revision = new RevisionReport(dataPreProcessing.getProfiles().size(),
		new QualitativeData(dataPreProcessing.getRevisionData()
			.getRevisions()), getMinFive(dataPreProcessing
			.getRevisionData().getRevisions()),
		getTopFive(dataPreProcessing.getRevisionData().getRevisions()));
    }

    private void evalStatus() {
	List<SimpleResultLong> statusResults = new ArrayList<>();
	statusResults.add(new SimpleResultLong("Alpha", dataPreProcessing
		.getStatusData().getAlpha()));
	statusResults.add(new SimpleResultLong("Beta", dataPreProcessing
		.getStatusData().getBeta()));
	statusResults.add(new SimpleResultLong("End of Lifetime",
		dataPreProcessing.getStatusData().getEol()));
	statusResults.add(new SimpleResultLong("Production", dataPreProcessing
		.getStatusData().getProduction()));

	status = new StatusReport(getTopFive(statusResults),
		new QualitativeData(dataPreProcessing.getStatusData()
			.getStatusSince()), getTopFive(dataPreProcessing
			.getStatusData().getStatusSince()),
		getMinFive(dataPreProcessing.getStatusData().getStatusSince()));
    }

    private void evalType() {
	type = new TypeReport(calcPercent(dataPreProcessing.getTypeData()
		.getSaasCentric(), profilesCount), calcPercent(
		dataPreProcessing.getTypeData().getGeneric(), profilesCount),
		calcPercent(dataPreProcessing.getTypeData().getIaasCentric(),
			profilesCount));
    }

    private void evalPlatform() {
	List<SimpleResultLong> platforms = dataPreProcessing
		.getPlatformData()
		.getPlatforms()
		.entrySet()
		.stream()
		.map(entry -> new SimpleResultLong(entry.getKey(), entry
			.getValue()))
		.collect(Collectors.toCollection(ArrayList::new));

	platform = new PlatformReport(calcPercent(dataPreProcessing
		.getPlatformData().getplatformProfiles(), profilesCount),
		getTopFive(platforms));
    }

    private void evalHosting() {
	hosting = new HostingReport(calcPercent(dataPreProcessing
		.getHostingData().getPrivate(), profilesCount), calcPercent(
		dataPreProcessing.getHostingData().getPublic(), profilesCount),
		calcPercent(dataPreProcessing.getHostingData()
			.getVirtualPrivate(), profilesCount));
    }

    private void evalPricing() {
	List<SimpleResultLong> numberOfModelsPerProfile = new ArrayList<>();
	numberOfModelsPerProfile.add(new SimpleResultLong("Zero Models",
		dataPreProcessing.getPricingData().getZeroModels()));
	numberOfModelsPerProfile.add(new SimpleResultLong("One Models",
		dataPreProcessing.getPricingData().getOneModel()));
	numberOfModelsPerProfile.add(new SimpleResultLong("Two Models",
		dataPreProcessing.getPricingData().getTwoModels()));
	numberOfModelsPerProfile.add(new SimpleResultLong("Three Models",
		dataPreProcessing.getPricingData().getThreeModels()));
	numberOfModelsPerProfile.add(new SimpleResultLong("Four Models",
		dataPreProcessing.getPricingData().getFourModels()));

	List<SimpleResultLong> models = new ArrayList<>();
	models.add(new SimpleResultLong("Free", dataPreProcessing
		.getPricingData().getFreeModel()));
	models.add(new SimpleResultLong("Fixed", dataPreProcessing
		.getPricingData().getFixedModel()));
	models.add(new SimpleResultLong("Metered", dataPreProcessing
		.getPricingData().getMeteredModel()));
	models.add(new SimpleResultLong("Hybrid", dataPreProcessing
		.getPricingData().getHybridModel()));
	models.add(new SimpleResultLong("Empty", dataPreProcessing
		.getPricingData().getEmptyModel()));

	List<SimpleResultLong> periods = new ArrayList<>();
	periods.add(new SimpleResultLong("Daily", dataPreProcessing
		.getPricingData().getDailyPeriod()));
	periods.add(new SimpleResultLong("Monthly", dataPreProcessing
		.getPricingData().getMonthlyPeriod()));
	periods.add(new SimpleResultLong("Anually", dataPreProcessing
		.getPricingData().getAnnuallyPariod()));
	periods.add(new SimpleResultLong("Empty", dataPreProcessing
		.getPricingData().getEmptyPeriod()));

	pricing = new PricingReport(getTopFive(numberOfModelsPerProfile),
		getTopFive(models), getTopFive(periods));
    }

    private void evalScaling() {
	scaling = new ScalingReport(calcPercent(dataPreProcessing
		.getScalingData().getVertical(), profilesCount), calcPercent(
		dataPreProcessing.getScalingData().getHorizontal(),
		profilesCount), calcPercent(dataPreProcessing.getScalingData()
		.getAuto(), profilesCount));
    }

    private void evalRuntimes() {
	long languageSpecific = dataPreProcessing.getRuntimesData()
		.getNumberPerProfile().entrySet().stream()
		.filter(entry -> entry.getValue() == 1)
		.collect(Collectors.summingLong(entry -> 1));
	long polyglot = dataPreProcessing.getRuntimesData()
		.getNumberPerProfile().entrySet().stream()
		.filter(entry -> entry.getValue() > 1)
		.collect(Collectors.summingLong(entry -> 1));

	List<SimpleResultDouble> runtimesShare = dataPreProcessing
		.getRuntimesData()
		.getRuntimes()
		.entrySet()
		.stream()
		.map(entry -> new SimpleResultDouble(entry.getKey(),
			calcPercent(entry.getValue(), profilesCount)))
		.collect(Collectors.toCollection(ArrayList::new));
	runtimesShare
		.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

	// Summarise all elements after the 9th and put it back into the List
	SimpleResultDouble otherRuntimes = new SimpleResultDouble("Others",
		runtimesShare
			.subList(9, runtimesShare.size())
			.stream()
			.collect(
				Collectors.summingDouble(entry -> entry
					.getValue())));
	runtimesShare = runtimesShare.subList(0, 9);
	runtimesShare.add(otherRuntimes);

	runtimes = new RuntimesReport(languageSpecific, polyglot,
		new QualitativeData(dataPreProcessing.getRuntimesData()
			.getNumberPerProfile()), getTopFive(dataPreProcessing
			.getRuntimesData().getNumberPerProfile()),
		runtimesShare);
    }

    private void evalMiddleware() {
	// TODO Auto-generated method stub
    }

    private void evalFrameworks() {
	// TODO Auto-generated method stub
    }

    private void evalServices() {
	// TODO Auto-generated method stub
    }

    private void evalExtensible() {
	// TODO Auto-generated method stub
    }

    private void evalInfrastructures() {
	// TODO Auto-generated method stub
    }

    // Private Methods that are used to calculate several results

    private List<SimpleResultLong> getTopFive(Map<String, Long> list) {
	List<SimpleResultLong> results = list
		.entrySet()
		.stream()
		.map(entry -> {
		    return new SimpleResultLong(entry.getKey(), entry
			    .getValue());
		}).collect(Collectors.toCollection(ArrayList::new));
	// Sort descending
	results.sort((x, y) -> Long.compare(y.getValue(), x.getValue()));
	// Return the Top 5 Elements
	if (list.size() >= 5) {
	    return results.subList(0, 5);
	} else
	    return results;
    }

    private List<SimpleResultLong> getTopFive(List<SimpleResultLong> list) {
	// Sort descending
	list.sort((x, y) -> Long.compare(y.getValue(), x.getValue()));
	// Return the Top 5 Elements
	if (list.size() >= 5) {
	    return list.subList(0, 5);
	} else
	    return list;
    }

    private List<SimpleResultLong> getMinFive(Map<String, Long> list) {
	List<SimpleResultLong> results = list
		.entrySet()
		.stream()
		.map(entry -> {
		    return new SimpleResultLong(entry.getKey(), entry
			    .getValue());
		}).collect(Collectors.toCollection(ArrayList::new));
	// Sort descending
	results.sort((x, y) -> Long.compare(x.getValue(), y.getValue()));
	// Return the Top 5 Elements
	if (list.size() >= 5) {
	    return results.subList(0, 5);
	} else
	    return results;
    }

    private List<SimpleResultLong> getMinFive(List<SimpleResultLong> list) {
	// Sort descending
	list.sort((x, y) -> Long.compare(x.getValue(), y.getValue()));
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
