package statistics;

import java.util.AbstractMap;
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

public class StatisticsImplWithModels implements StatisticsWithModels {

	@Override
	public RevisionReport evalRevision(Map<String, Long> data) {
		double[] values = data.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(values);
		return new RevisionReport(values.length, StatUtils.mean(values), new Median().evaluate(values), variance,
				Math.sqrt(variance), getMinFiveLong(data), getTopFiveLong(data));
	}

	@Override
	public StatusReport evalStatus(Map<String, Long> data) {
		return new StatusReport(data.get("size").intValue(), data.get("production"), data.get("alpha"),
				data.get("beta"), getMinFiveLong(data), getTopFiveLong(data));
	}

	@Override
	public StatusSinceReport evalStatusSince(Map<String, Long> data) {
		double[] values = data.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(values);
		return new StatusSinceReport(values.length, StatUtils.mean(values), new Median().evaluate(values), variance,
				Math.sqrt(variance), getMinFiveLong(data), getTopFiveLong(data));
	}

	@Override
	public TypeReport evalType(Map<String, Long> data) {
		return new TypeReport(data.get("size"), data.get("SaaS-centric"), data.get("Generic"), data.get("IaaS-centric"),
				getMinFiveLong(data), getTopFiveLong(data));
	}

	@Override
	public QosReport evalQos(Map<String, Double> data) {
		double[] values = data.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(values);
		return new QosReport(values.length, StatUtils.mean(values), new Median().evaluate(values), variance,
				Math.sqrt(variance), getMinFiveDouble(data), getTopFiveDouble(data));
	}

	@Override
	public OverallComplianceReport evalOverallCompliance(Map<String, Long> data) {
		double[] values = data.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		double variance = StatUtils.populationVariance(values);

		// Collect all compliances into a list
		List<Map.Entry<String, Long>> entries = data.entrySet().stream()
				.filter(entry -> entry.getKey().startsWith("comp|"))
				.map(e -> new AbstractMap.SimpleEntry<String, Long>(e.getKey().substring(e.getKey().indexOf("-") + 1),
						e.getValue()))
				.collect(Collectors.toCollection(ArrayList::new));

		return new OverallComplianceReport(values.length, getMinFiveLong(data), getTopFiveLong(data), entries);
	}

	@Override
	public SpecificComplianceReport evalSpecificCompliance(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlatformReport evalPlatform(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HostingReport evalHosting(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PricingReport evalPricing(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScalingReport evalScaling(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuntimesReport evalRuntimes(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MiddlewareReport evalMiddleware(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FrameworksReport evalFrameworks(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServicesReport evalServices(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtensibleReport evalExtensible(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfrastructuresReport evalInfrastructures(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
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
