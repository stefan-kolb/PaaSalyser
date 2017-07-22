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
import report.models.OverallComplianceReport;
import report.models.PlatformReport;
import report.models.PricingReport;
import report.models.QosReport;
import report.models.RevisionReport;
import report.models.RuntimesReport;
import report.models.ScalingReport;
import report.models.ServicesReport;
import report.models.SpecificComplianceReport;
import report.models.StatusReport;
import report.models.StatusSinceReport;
import report.models.TypeReport;

public class StatisticsImplWithModels {

	private DataPreprocessing dataPreProcessing;

	private int profilesCount;
	private RevisionReport revision;
	private StatusReport status;
	private StatusSinceReport statusSince;
	private TypeReport type;
	private QosReport qos;
	private OverallComplianceReport overallCompliance;
	private SpecificComplianceReport specificCompliance;
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
		evalRevision();
		evalStatus();
		evalStatusSince();
		evalType();
		evalQos();
		evalOverallCompliance();
		evalSpecificCompliance();
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

	public int profilesCount() {
		return profilesCount;
	}

	public RevisionReport getRevision() {
		return revision;
	}

	public StatusReport getStatus() {
		return status;
	}

	public StatusSinceReport getStatusSince() {
		return statusSince;
	}

	public TypeReport getType() {
		return type;
	}

	public QosReport getQos() {
		return qos;
	}

	public OverallComplianceReport getOverallCompliance() {
		return overallCompliance;
	}

	public SpecificComplianceReport getSpecificCompliance() {
		return specificCompliance;
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
		double[] values = dataPreProcessing.getRevision().entrySet().stream()
				.mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(values);
		revision = new RevisionReport(values.length, StatUtils.mean(values), new Median().evaluate(values), variance,
				Math.sqrt(variance), getMinFiveLong(dataPreProcessing.getRevision()),
				getTopFiveLong(dataPreProcessing.getRevision()));
	}

	private void evalStatus() {
		status = new StatusReport(dataPreProcessing.getStatus().get("size").intValue(),
				dataPreProcessing.getStatus().get("production"), dataPreProcessing.getStatus().get("alpha"),
				dataPreProcessing.getStatus().get("beta"), getMinFiveLong(dataPreProcessing.getStatus()),
				getTopFiveLong(dataPreProcessing.getStatus()));
	}

	private void evalStatusSince() {
		double[] values = dataPreProcessing.getStatusSince().entrySet().stream()
				.mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(values);
		statusSince = new StatusSinceReport(values.length, StatUtils.mean(values), new Median().evaluate(values),
				variance, Math.sqrt(variance), getMinFiveLong(dataPreProcessing.getStatusSince()),
				getTopFiveLong(dataPreProcessing.getStatusSince()));
	}

	private void evalType() {
		type = new TypeReport(dataPreProcessing.getType().get("size"), dataPreProcessing.getType().get("SaaS-centric"),
				dataPreProcessing.getType().get("Generic"), dataPreProcessing.getType().get("IaaS-centric"),
				getMinFiveLong(dataPreProcessing.getType()), getTopFiveLong(dataPreProcessing.getType()));
	}

	private void evalQos() {
		double[] values = dataPreProcessing.getQos().entrySet().stream().mapToDouble(e -> e.getValue().doubleValue())
				.toArray();
		double variance = StatUtils.populationVariance(values);
		qos = new QosReport(values.length, StatUtils.mean(values), new Median().evaluate(values), variance,
				Math.sqrt(variance), getMinFiveDouble(dataPreProcessing.getQos()),
				getTopFiveDouble(dataPreProcessing.getQos()));
	}

	private void evalOverallCompliance() {
		// Collect all profiles with compliances into a list to evaluate
		Map<String, Long> entries = dataPreProcessing.getCompliance().entrySet().stream()
				//
				.filter(entry -> entry.getKey().startsWith("#c-"))
				.collect(Collectors.toMap(entry -> (entry.getKey().substring(entry.getKey().indexOf("-") + 1)),
						entry -> entry.getValue()));

		double[] values = entries.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(values);

		double percentWithCompliances = (entries.size() / dataPreProcessing.getCompliance().size()) * 100;

		overallCompliance = new OverallComplianceReport(entries.size(), StatUtils.mean(values),
				new Median().evaluate(values), variance, Math.sqrt(variance), getMinFiveLong(entries),
				getTopFiveLong(entries), percentWithCompliances);
	}

	private void evalSpecificCompliance() {
		Map<String, Long> entries = dataPreProcessing.getCompliance().entrySet().stream()
				//
				.filter(entry -> entry.getKey().startsWith("comp|"))
				.collect(Collectors.toMap(entry -> (entry.getKey().substring(entry.getKey().indexOf("|") + 1)),
						entry -> entry.getValue()));
		specificCompliance = new SpecificComplianceReport(entries.size(), getMinFiveLong(entries),
				getTopFiveLong(entries));
	}

	private void evalPlatform() {
		platform = new PlatformReport(dataPreProcessing.getPlatform().size(),
				getMinFiveLong(dataPreProcessing.getPlatform()), getTopFiveLong(dataPreProcessing.getPlatform()));
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

	private List<Map.Entry<String, Long>> getTopFiveLong(Map<String, Long> data) {
		List<Map.Entry<String, Long>> results = new ArrayList<>(data.size());

		results.addAll(data.entrySet());

		// Sort descending
		results.sort((x, y) -> y.getValue().compareTo(x.getValue()));
		System.out.println("Results top: " + results);

		// Return the Top 5 Elements
		if (results.size() >= 5) {
			return results.subList(0, 4);
		} else
			return results;
	}

	private List<Map.Entry<String, Long>> getMinFiveLong(Map<String, Long> data) {
		List<Map.Entry<String, Long>> results = new ArrayList<>(data.size());

		results.addAll(data.entrySet());

		// Sort descending
		results.sort((x, y) -> x.getValue().compareTo(y.getValue()));
		System.out.println("Results min: " + results);

		// Return the Min 5 Elements
		if (results.size() >= 5) {
			return results.subList(0, 4);
		} else
			return results;
	}

	private List<Map.Entry<String, Double>> getTopFiveDouble(Map<String, Double> data) {
		List<Map.Entry<String, Double>> results = new ArrayList<>(data.size());

		results.addAll(data.entrySet());

		// Sort descending
		results.sort((x, y) -> y.getValue().compareTo(x.getValue()));
		System.out.println("Results top: " + results);

		// Return the Top 5 Elements
		if (results.size() >= 5) {
			return results.subList(0, 4);
		} else
			return results;
	}

	private List<Map.Entry<String, Double>> getMinFiveDouble(Map<String, Double> data) {
		List<Map.Entry<String, Double>> results = new ArrayList<>(data.size());

		results.addAll(data.entrySet());

		// Sort descending
		results.sort((x, y) -> x.getValue().compareTo(y.getValue()));
		System.out.println("Results min: " + results);

		// Return the Min 5 Elements
		if (results.size() >= 5) {
			return results.subList(0, 4);
		} else
			return results;
	}

}
