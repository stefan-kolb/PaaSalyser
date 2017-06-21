package statistics;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;

public class StatisticsImpl implements Statistics {

	// As every profile has a revision this gets set when evalRevision is
	// evaluated
	int profilesCount = 0;

	@Override
	public Map<String, Double> evalRevision(Map<String, Long> data) {

		// Set number of profiles
		profilesCount = data.size();

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
	public Map<String, Long> evalStatus(Map<String, Long> data) {
		Map<String, Long> results = new HashMap<String, Long>();

		// Remove size from the actual values
		results.put("size", data.remove("size"));
		results.put("production", data.get("production"));
		results.put("alpha", data.get("alpha"));
		results.put("beta", data.get("beta"));
		results.put("mode", (long) 0);

		long modeCount = 0;
		results.put("mode" + modeCount, (long) 0);

		for (Entry<String, Long> entry : data.entrySet()) {

			if (!entry.getKey().equals("null")) {
				results.put(entry.getKey(), entry.getValue());

				if (entry.getValue() > results.get("mode0")) {

					// Falls es nur einen Modus gibt
					if (modeCount == 0) {
						results.replace("mode" + modeCount, results.get("mode" + modeCount), entry.getValue());
					} else if (modeCount > 0) {
						// Falls es mehr als einen Modus gibt
						for (; modeCount > 0; modeCount--) {
							// Lösche alle Modi um wieder bei mode0 zu beginnen
							results.remove("mode" + modeCount);
						}
						results.put("mode0", entry.getValue());
					}
				} else if (entry.getValue() == results.get("mode")) {
					// Add another mode
					modeCount++;
					results.put("mode" + modeCount, entry.getValue());
				}

			} else {
				results.put("noPlatform", entry.getValue());
			}
		}
		results.put("modeCount", modeCount);

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

		// Put data of map in according list to make a double[] out of it later
		for (Entry<String, Double> entry : data.entrySet()) {
			if (entry.getKey().startsWith("qos")) {
				qosList.add(entry.getValue());
			}
		}

		// Put Values into double array because apache.commoms.maths
		// computations use double arrays
		double[] qosValues = new double[qosList.size()];
		int i = 0;
		for (Double entry : qosList) {
			qosValues[i] = entry.doubleValue();
			i++;
		}

		// Evaluate Qos
		results.put("size", (double) qosValues.length);
		results.put("percentWithQos", (double) data.size());
		results.put("mean", StatUtils.mean(qosValues));
		results.put("median", new Median().evaluate(qosValues));
		i = 0;
		for (double mode : StatUtils.mode(qosValues)) {
			results.put("mode" + i, mode);
			i++;
		}
		results.put("variance", StatUtils.populationVariance(qosValues));
		results.put("stdev", Math.sqrt(results.get("variance")));
		results.put("min", StatUtils.min(qosValues));
		results.put("max", StatUtils.max(qosValues));

		return results;
	}

	@Override
	public Map<String, Double> evalOverallCompliance(Map<String, Long> data) {
		Map<String, Double> results = new HashMap<String, Double>();

		List<Long> compList = new LinkedList<Long>();

		// Put data of map in according list to make a double[] out of it later
		for (Entry<String, Long> entry : data.entrySet()) {
			if (entry.getKey().startsWith("#c-")) {
				compList.add(entry.getValue());
			}
		}
		// Put Values into double array because apache.commoms.maths
		// computations use double arrays
		double[] compValues = new double[compList.size()];
		int i = 0;
		for (Long entry : compList) {
			compValues[i] = entry.doubleValue();
			i++;
		}

		// Compute overall Statistics of Compliance
		results.put("size", (double) data.get("size"));
		results.put("percentWithCompliance", (double) (data.get("size") / profilesCount) * 100);
		results.put("mean", StatUtils.mean(compValues));
		results.put("median", new Median().evaluate(compValues));
		i = 0;
		for (double mode : StatUtils.mode(compValues)) {
			results.put("mode" + i, mode);
			i++;
		}
		results.put("variance", StatUtils.populationVariance(compValues));
		results.put("stdev", Math.sqrt(results.get("variance")));
		results.put("min", StatUtils.min(compValues));
		results.put("max", StatUtils.max(compValues));

		return results;
	}

	@Override
	public Map<String, String> evalSpecificCompliance(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		List<Entry<String, Long>> compList = new LinkedList<Entry<String, Long>>();

		for (Entry<String, Long> entry : data.entrySet()) {
			if (entry.getKey().startsWith("comp|")) {
				compList.add(new AbstractMap.SimpleEntry<String, Long>(
						entry.getKey().substring(entry.getKey().indexOf("|") + 1), entry.getValue()));
			}
		}

		results.put("mode", "");
		results.put("min", "");

		long max = 0;
		long min = Long.MAX_VALUE;
		for (Entry<String, Long> entry : compList) {
			if (entry.getValue() > max) {
				max = entry.getValue();
				results.replace("mode", results.get("mode"), entry.getKey() + "|" + entry.getValue());
			} else if (entry.getValue() == max) {
				results.replace("mode", results.get("mode"),
						results.get("mode").substring(0, results.get("mode").indexOf("|")) + "&" + entry.getKey() + "|"
								+ entry.getValue());
			}
			if (entry.getValue() < min) {
				min = entry.getValue();
				results.replace("min", results.get("min"), entry.getKey() + "|" + entry.getValue());
			} else if (entry.getValue() == min) {
				results.replace("min", results.get("min"),
						results.get("min").substring(0, results.get("min").indexOf("|")) + "&" + entry.getKey() + "|"
								+ entry.getValue());
			}
		}

		return results;
	}

	@Override
	public Map<String, Long> evalPlatform(Map<String, Long> data) {
		Map<String, Long> results = new HashMap<String, Long>();

		results.putAll(getQualitativeMode(data));

		long modeCount = 0;
		results.put("mode" + modeCount, (long) 0);

		for (Entry<String, Long> entry : data.entrySet()) {

			if (!entry.getKey().equals("null")) {
				results.put(entry.getKey(), entry.getValue());

				if (entry.getValue() > results.get("mode0")) {

					// Falls es nur einen Modus gibt
					if (modeCount == 0) {
						results.replace("mode" + modeCount, results.get("mode" + modeCount), entry.getValue());
					} else if (modeCount > 0) {
						// Falls es mehr als einen Modus gibt
						for (; modeCount > 0; modeCount--) {
							// Lösche alle Modi um wieder bei mode0 zu beginnen
							results.remove("mode" + modeCount);
						}
						results.put("mode0", entry.getValue());
					}
				} else if (entry.getValue() == results.get("mode")) {
					// Add another mode
					modeCount++;
					results.put("mode" + modeCount, entry.getValue());
				}

			} else {
				results.put("noPlatform", entry.getValue());
			}
		}
		results.put("modeCount", modeCount);

		return results;
	}

	@Override
	public Map<String, Long> evalHosting(Map<String, Long> data) {
		Map<String, Long> results = new HashMap<String, Long>();

		results.putAll(getQualitativeMode(data));
		results.putAll(data);

		return results;
	}

	@Override
	public Map<String, Long> evalPricing(Map<String, Long> data) {
		Map<String, Long> results = new HashMap<String, Long>();

		// Evaluate PricingModel
		Map<String, Long> tempMap = new HashMap<String, Long>();

		Predicate<Entry<String, Long>> isModel = entry -> entry.getKey().startsWith("model");
		Predicate<Entry<String, Long>> isCounter = entry -> entry.getKey().contains("counter");

		// Compute modelCounterMode
		data.entrySet().stream().filter(isModel.and(isCounter))
				.map(entry -> tempMap.put(entry.getKey(), entry.getValue()));
		System.out.println("xyz");
		System.out.println(tempMap);
		getQualitativeMode(tempMap).entrySet().stream()
				.map(entry -> results.put("modelcountermode|" + entry.getKey(), entry.getValue()));
		System.out.println(getQualitativeMode(tempMap));

		// Compute modelMode
		tempMap.clear();
		data.entrySet().stream()
				.filter(entry -> entry.getKey().startsWith("model") && !entry.getKey().contains("counter"))
				.map(entry -> tempMap.put(entry.getKey(), entry.getValue()));
		getQualitativeMode(tempMap).entrySet().stream()
				.map(entry -> results.put("modelmode|" + entry.getKey(), entry.getValue()));

		return results;
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

	private Map<String, Long> getQualitativeMode(Map<String, Long> data) {
		Map<String, Long> results = new HashMap<String, Long>();

		long modeCount = 1;
		results.put("mode" + (modeCount - 1), (long) 0);

		for (Entry<String, Long> entry : data.entrySet()) {

			if (!entry.getKey().equals("null")) {

				if (entry.getValue() > results.get("mode" + (modeCount - 1))) {

					// Falls es nur einen Modus gibt
					if (modeCount == 1) {
						results.replace("mode" + (modeCount - 1), results.get("mode" + (modeCount - 1)),
								entry.getValue());
					} else if (modeCount > 1) {
						// Falls es mehr als einen Modus gibt
						for (; modeCount > 1; modeCount--) {
							// Lösche alle Modi um wieder bei mode0 zu beginnen
							results.remove("mode" + (modeCount - 1));
						}
						// Setze anschließend wieder mode0
						results.put("mode" + (modeCount - 1), entry.getValue());
					}
				} else if (entry.getValue() == results.get("mode" + (modeCount - 1))) {
					// Add another mode
					modeCount++;
					results.put("mode" + (modeCount - 1), entry.getValue());
				}

			}
		}
		results.put("modeCount", modeCount);
		return results;
	}

}
