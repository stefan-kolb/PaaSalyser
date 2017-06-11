package statistics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;

public class StatisticsImpl implements Statistics {

	@Override
	public Map<String, Double> evalRevision(Map<String, Long> data) {
		Map<String, Double> results = new HashMap<String, Double>();

		double[] values = new double[data.size()];

		int i = 0;
		for (Long value : data.values()) {
			values[i] = value.doubleValue();
			i++;
		}

		results.put("size", (double) values.length);
		results.put("mean", StatUtils.mean(values));
		results.put("median", new Median().evaluate(values));
		i = 0;
		for (double mode : StatUtils.mode(values)) {
			results.put("mode" + i, mode);
			i++;
		}
		results.put("variance", StatUtils.populationVariance(values));
		results.put("stdev", Math.sqrt(results.get("variance")));
		results.put("min", StatUtils.min(values));
		results.put("max", StatUtils.max(values));
		return results;
	}

	@Override
	public Map<String, String> evalStatus(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		// Remove size from the actual values
		results.put("size", "" + data.remove("size"));
		results.put("production", "" + data.get("production"));
		results.put("alpha", "" + data.get("alpha"));
		results.put("beta", "" + data.get("beta"));
		results.put("mode", "");

		long max = 0;
		for (Entry<String, Long> entry : data.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
				results.replace("mode", results.get("mode"), entry.getKey() + "|" + entry.getValue());
			} else if (entry.getValue() == max) {
				// Add the current entries' name if its value is equal
				// to the current max value
				results.replace("mode", results.get("mode"),
						results.get("mode").substring(0, results.get("mode").indexOf("|")) + "," + entry.getKey() + "|"
								+ entry.getValue());
			}
		}

		return results;
	}

	@Override
	public Map<String, Double> evalStatusSince(Map<String, Long> data) {
		Map<String, Double> results = new HashMap<String, Double>();

		double[] values = new double[data.size()];

		int i = 0;
		for (Long value : data.values()) {
			values[i] = value.doubleValue();
			i++;
		}

		results.put("size", (double) values.length);
		results.put("mean", StatUtils.mean(values));
		results.put("median", new Median().evaluate(values));
		i = 0;
		for (double mode : StatUtils.mode(values)) {
			results.put("mode" + i, mode);
			i++;
		}
		results.put("variance", StatUtils.populationVariance(values));
		results.put("stdev", Math.sqrt(results.get("variance")));
		results.put("min", StatUtils.min(values));
		results.put("max", StatUtils.max(values));
		return results;
	}

	@Override
	public Map<String, String> evalType(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		// Remove size from the actual values
		results.put("size", "" + data.remove("size"));
		results.put("SaaS-centric", "" + data.get("SaaS-centric"));
		results.put("Generic", "" + data.get("Generic"));
		results.put("IaaS-centric", "" + data.get("IaaS-centric"));
		results.put("mode", "");

		long max = 0;
		for (Entry<String, Long> entry : data.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
				results.replace("mode", results.get("mode"), entry.getKey() + "|" + entry.getValue());
			} else if (entry.getValue() == max) {
				results.replace("mode", results.get("mode"),
						results.get("mode").substring(0, results.get("mode").indexOf("|")) + "," + entry.getKey() + "|"
								+ entry.getValue());
			}
		}

		return results;
	}

	@Override
	public Map<String, Double> evalQos(Map<String, Double> data) {
		Map<String, Double> results = new HashMap<String, Double>();
		List<Double> qosList = new LinkedList<Double>();

		// Evaluate the Qos
		
		for (Entry<String, Double> entry : data.entrySet()) {
			if (entry.getKey().startsWith("qos")) {
				qosList.add(entry.getValue());
			}
		}

		double[] values = new double[qosList.size()];
		int i = 0;
		for (Double entry : qosList) {
			values[i] = entry.doubleValue();
			i++;
		}

		results.put("qossize", (double) values.length);
		results.put("qosmean", StatUtils.mean(values));
		results.put("qosmedian", new Median().evaluate(values));
		i = 0;
		for (double mode : StatUtils.mode(values)) {
			results.put("qosmode" + i, mode);
			i++;
		}
		results.put("qosvariance", StatUtils.populationVariance(values));
		results.put("qosstdev", Math.sqrt(results.get("qosvariance")));
		results.put("qosmin", StatUtils.min(values));
		results.put("qosmax", StatUtils.max(values));
		
		// Evaluate the Compliance
		data.entrySet().forEach(entry -> {
			if (!entry.getKey().startsWith("qos")) {
			}
		});

		return results;
	}

	@Override
	public Map<String, Double> evalPlatform(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalHosting(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalPricing(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalScaling(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalRuntimes(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalMiddleware(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalFrameworks(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalServices(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalExtensible(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> evalInfrastructures(Map<String, Long> data) {
		// TODO Auto-generated method stub
		return null;
	}

}
