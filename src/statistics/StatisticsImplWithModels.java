package statistics;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import report.models.ExtensibleReport;
import report.models.FrameworksReport;
import report.models.HostingReport;
import report.models.InfrastructuresReport;
import report.models.MiddlewareReport;
import report.models.PlatformReport;
import report.models.PricingReport;
import report.models.RevisionReport;
import report.models.RuntimesReport;
import report.models.ScalingReport;
import report.models.ServicesReport;
import report.models.StatusReport;
import report.models.TypeReport;
import statistics.models.SimpleResult;

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
		double[] values = dataPreProcessing.getRevisionData().getRevisions().entrySet().stream()
				.mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(values);

		revision = new RevisionReport(values.length, StatUtils.mean(values), new Median().evaluate(values), variance,
				Math.sqrt(variance), getMinFive(dataPreProcessing.getRevisionData().getRevisions()),
				getTopFive(dataPreProcessing.getRevisionData().getRevisions()));
	}

	private void evalStatus() {
		List<SimpleResult> statusResults = new ArrayList<>();
		statusResults.add(new SimpleResult("Alpha", dataPreProcessing.getStatusData().getAlpha()));
		statusResults.add(new SimpleResult("Beta", dataPreProcessing.getStatusData().getBeta()));
		statusResults.add(new SimpleResult("End of Lifetime", dataPreProcessing.getStatusData().getEol()));
		statusResults.add(new SimpleResult("Production", dataPreProcessing.getStatusData().getProduction()));

		double[] statusSinceValues = dataPreProcessing.getStatusData().getStatusSince().entrySet().stream()
				.mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(statusSinceValues);

		status = new StatusReport(getTopFive(statusResults), StatUtils.mean(statusSinceValues),
				new Median().evaluate(statusSinceValues), variance, Math.sqrt(variance),
				getTopFive(dataPreProcessing.getStatusData().getStatusSince()),
				getMinFive(dataPreProcessing.getStatusData().getStatusSince()));
	}

	private void evalType() {
		type = new TypeReport(calcPercent(dataPreProcessing.getTypeData().getSaasCentric(), profilesCount),
				calcPercent(dataPreProcessing.getTypeData().getGeneric(), profilesCount),
				calcPercent(dataPreProcessing.getTypeData().getIaasCentric(), profilesCount));
	}

	private void evalPlatform() {
		List<SimpleResult> platforms = dataPreProcessing.getPlatformData().getPlatforms().entrySet().stream()
				.map(entry -> {
					return new SimpleResult(entry.getKey(), entry.getValue());
				}).collect(Collectors.toCollection(ArrayList::new));

		platform = new PlatformReport(
				calcPercent(dataPreProcessing.getPlatformData().getplatformProfiles(), profilesCount),
				getTopFive(platforms));
	}

	private void evalHosting() {
		hosting = new HostingReport(calcPercent(dataPreProcessing.getHostingData().getPrivate(), profilesCount),
				calcPercent(dataPreProcessing.getHostingData().getPublic(), profilesCount),
				calcPercent(dataPreProcessing.getHostingData().getVirtualPrivate(), profilesCount));
	}

	private void evalPricing() {
		List<SimpleResult> numberOfModelsPerProfile = new ArrayList<>();
		numberOfModelsPerProfile
				.add(new SimpleResult("Zero Models", dataPreProcessing.getPricingData().getZeroModels()));
		numberOfModelsPerProfile.add(new SimpleResult("One Models", dataPreProcessing.getPricingData().getOneModel()));
		numberOfModelsPerProfile.add(new SimpleResult("Two Models", dataPreProcessing.getPricingData().getTwoModels()));
		numberOfModelsPerProfile
				.add(new SimpleResult("Three Models", dataPreProcessing.getPricingData().getThreeModels()));
		numberOfModelsPerProfile
				.add(new SimpleResult("Four Models", dataPreProcessing.getPricingData().getFourModels()));

		List<SimpleResult> models = new ArrayList<>();
		models.add(new SimpleResult("Free", dataPreProcessing.getPricingData().getFreeModel()));
		models.add(new SimpleResult("Fixed", dataPreProcessing.getPricingData().getFixedModel()));
		models.add(new SimpleResult("Metered", dataPreProcessing.getPricingData().getMeteredModel()));
		models.add(new SimpleResult("Hybrid", dataPreProcessing.getPricingData().getHybridModel()));
		models.add(new SimpleResult("Empty", dataPreProcessing.getPricingData().getEmptyModel()));

		List<SimpleResult> periods = new ArrayList<>();
		periods.add(new SimpleResult("Daily", dataPreProcessing.getPricingData().getDailyPeriod()));
		periods.add(new SimpleResult("Monthly", dataPreProcessing.getPricingData().getMonthlyPeriod()));
		periods.add(new SimpleResult("Anually", dataPreProcessing.getPricingData().getAnnuallyPariod()));
		periods.add(new SimpleResult("Empty", dataPreProcessing.getPricingData().getEmptyPeriod()));

		pricing = new PricingReport(getTopFive(numberOfModelsPerProfile), getTopFive(models), getTopFive(periods));
	}

	private void evalScaling() {
		scaling = new ScalingReport(calcPercent(dataPreProcessing.getScalingData().getVertical(), profilesCount),
				calcPercent(dataPreProcessing.getScalingData().getHorizontal(), profilesCount),
				calcPercent(dataPreProcessing.getScalingData().getAuto(), profilesCount));
	}

	private void evalRuntimes() {
		// TODO Auto-generated method stub
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

	private List<SimpleResult> getTopFive(Map<String, Long> list) {
		List<SimpleResult> results = list.entrySet().stream().map(entry -> {
			return new SimpleResult(entry.getKey(), entry.getValue());
		}).collect(Collectors.toCollection(ArrayList::new));
		// Sort descending
		results.sort((x, y) -> Long.compare(y.getValue(), x.getValue()));
		// Return the Top 5 Elements
		if (list.size() >= 5) {
			return results.subList(0, 5);
		} else
			return results;
	}

	private List<SimpleResult> getTopFive(List<SimpleResult> list) {
		// Sort descending
		list.sort((x, y) -> Long.compare(y.getValue(), x.getValue()));
		// Return the Top 5 Elements
		if (list.size() >= 5) {
			return list.subList(0, 5);
		} else
			return list;
	}

	private List<SimpleResult> getMinFive(Map<String, Long> list) {
		List<SimpleResult> results = list.entrySet().stream().map(entry -> {
			return new SimpleResult(entry.getKey(), entry.getValue());
		}).collect(Collectors.toCollection(ArrayList::new));
		// Sort descending
		results.sort((x, y) -> Long.compare(x.getValue(), y.getValue()));
		// Return the Top 5 Elements
		if (list.size() >= 5) {
			return results.subList(0, 5);
		} else
			return results;
	}

	private List<SimpleResult> getMinFive(List<SimpleResult> list) {
		// Sort descending
		list.sort((x, y) -> Long.compare(x.getValue(), y.getValue()));
		// Return the Min 5 Elements
		if (list.size() >= 5) {
			return list.subList(0, 5);
		} else
			return list;
	}

	private double calcPercent(Number a, Number b) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		return (a.doubleValue() / b.doubleValue()) * 100;
	}

}
