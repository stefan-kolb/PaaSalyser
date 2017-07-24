package statistics;

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
		profilesCount = dataPreProcessing.getProfiles().size();
		eolProfiles = (int) dataPreProcessing.getStatusData().getEol();
		evalRevision();
		evalStatus();
		evalType();
		evalPlatform();
		evalHosting();
		evalPricing();
		evalScaling();
		evalRuntimes();
		evalMiddleware();
		evalFrameworks();
		evalServices();
		evalExtensible();
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
		List<SimpleResult> statusResults = new ArrayList<>();
		statusResults.add(new SimpleResult("SaaS-Centric", dataPreProcessing.getTypeData().getSaasCentric()));
		statusResults.add(new SimpleResult("Generic", dataPreProcessing.getTypeData().getGeneric()));
		statusResults.add(new SimpleResult("IaaS-Centric", dataPreProcessing.getTypeData().getIaasCentric()));

		type = new TypeReport(getTopFive(statusResults));
	}

	private void evalPlatform() {
		List<SimpleResult> platforms = dataPreProcessing.getPlatformData().getPlatforms().entrySet().stream()
				.map(entry -> {
					return new SimpleResult(entry.getKey(), entry.getValue());
				}).collect(Collectors.toCollection(ArrayList::new));
		
		double platformProfilesPercent = dataPreProcessing.getPlatformData().getplatformProfiles() / profilesCount;

		platform = new PlatformReport(platformProfilesPercent, getTopFive(platforms));
	}

	private void evalHosting() {
		// TODO Auto-generated method stub
	}

	private void evalPricing() {
		// TODO Auto-generated method stub
	}

	private void evalScaling() {
		// TODO Auto-generated method stub
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

}
