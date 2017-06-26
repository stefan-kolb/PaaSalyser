package statistics;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	public Map<String, String> evalPlatform(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		results.putAll(data.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> "" + e.getValue())));

		results.putAll(getQualitativeMode(data).entrySet().stream()
				.collect(Collectors.toMap(e -> "platformMode|" + e.getKey(), e -> e.getValue())));

		return results;
	}

	@Override
	public Map<String, String> evalHosting(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		results.putAll(data.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> "" + e.getValue())));
		results.putAll(getQualitativeMode(data).entrySet().stream()
				.collect(Collectors.toMap(e -> "hostingMode|" + e.getKey(), e -> e.getValue())));

		return results;
	}

	@Override
	public Map<String, String> evalPricing(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();
		Map<String, Long> tempMap;

		results.putAll(data.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> "" + e.getValue())));

		// Evaluate PricingModel
		Predicate<Entry<String, Long>> isModel = entry -> entry.getKey().startsWith("model");
		Predicate<Entry<String, Long>> isCounter = entry -> entry.getKey().contains("counter");
		Predicate<Entry<String, Long>> isPeriod = entry -> entry.getKey().startsWith("period");

		// Compute modelCounterMode
		tempMap = data.entrySet().stream().filter(isModel.and(isCounter))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		results.putAll(getQualitativeMode(tempMap).entrySet().stream()
				.collect(Collectors.toMap(e -> "modelcountermode|" + e.getKey(), e -> "" + e.getValue())));

		// Compute modelMode
		tempMap = data.entrySet().stream().filter(isModel.and(isCounter.negate()))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		results.putAll(getQualitativeMode(tempMap).entrySet().stream()
				.collect(Collectors.toMap(e -> "modelmode|" + e.getKey(), e -> "" + e.getValue())));

		// Compute periodMode
		tempMap = data.entrySet().stream().filter(isPeriod)
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		results.putAll(getQualitativeMode(tempMap).entrySet().stream()
				.collect(Collectors.toMap(e -> "periodmode|" + e.getKey(), e -> "" + e.getValue())));

		return results;
	}

	@Override
	public Map<String, String> evalScaling(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		results.putAll(data.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> "" + e.getValue())));

		results.putAll(getQualitativeMode(data).entrySet().stream()
				.collect(Collectors.toMap(e -> "scalingMode|" + e.getKey(), e -> e.getValue())));

		return results;
	}

	@Override
	public Map<String, String> evalRuntimes(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		// results.putAll(data.entrySet().stream().collect(Collectors.toMap(e ->
		// e.getKey(), e -> "" + e.getValue())));

		// Scan for programming languages
		data.entrySet().forEach(entry -> {
			// Check for current language
			if (entry.getKey().contains("|") && !entry.getKey().contains("Amount")) {
				String currentLanguage = entry.getKey().substring(0, entry.getKey().indexOf("|"));

				// Count each language and mark it with a "."
				if (results.putIfAbsent("." + currentLanguage, "" + 1) != null) {
					results.replace("." + currentLanguage,
							Long.toString((Long.parseLong(results.get("." + currentLanguage)) + 1)));
				}
			}
		});

		double[] runAmounts = data.entrySet().stream().filter(e -> e.getKey().startsWith("runAmount"))
				.mapToDouble(e -> e.getValue().doubleValue()).toArray();
		results.put("runMean", "" + StatUtils.mean(runAmounts));
		results.put("runVar", "" + StatUtils.populationVariance(runAmounts));
		results.put("runStdev", "" + Math.sqrt(Double.parseDouble(results.get("runVar"))));
		results.put("runStdev", "" + new Median().evaluate(runAmounts));
		results.put("runMin", "" + StatUtils.min(runAmounts));
		results.put("runMax", "" + StatUtils.max(runAmounts));

		double[] verAmounts = data.entrySet().stream().filter(e -> e.getKey().startsWith("verAmount"))
				.mapToDouble(e -> e.getValue().doubleValue()).toArray();
		results.put("verMean", "" + StatUtils.mean(verAmounts));
		results.put("verVar", "" + StatUtils.populationVariance(verAmounts));
		results.put("verStdev", "" + Math.sqrt(Double.parseDouble(results.get("verVar"))));
		results.put("verStdev", "" + new Median().evaluate(verAmounts));
		results.put("verMin", "" + StatUtils.min(verAmounts));
		results.put("verMax", "" + StatUtils.max(verAmounts));

		return results;
	}

	@Override
	public Map<String, String> evalMiddleware(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		// results.putAll(data.entrySet().stream().collect(Collectors.toMap(e ->
		// e.getKey(), e -> "" + e.getValue())));

		// Scan for Middlewares
		data.entrySet().forEach(entry -> {
			// Check for current language
			if (entry.getKey().contains("|") && !entry.getKey().contains("Amount")) {
				String currentLanguage = entry.getKey().substring(0, entry.getKey().indexOf("|"));
				if (results.putIfAbsent("." + currentLanguage, "" + 1) != null) {
					results.replace("." + currentLanguage,
							Long.toString((Long.parseLong(results.get("." + currentLanguage)) + 1)));
				}
			}
		});

		double[] midAmounts = data.entrySet().stream().filter(e -> e.getKey().startsWith("midAmount"))
				.mapToDouble(e -> e.getValue().doubleValue()).toArray();
		results.put("midMean", "" + StatUtils.mean(midAmounts));
		results.put("midVar", "" + StatUtils.populationVariance(midAmounts));
		results.put("midStdev", "" + Math.sqrt(Double.parseDouble(results.get("midVar"))));
		results.put("midStdev", "" + new Median().evaluate(midAmounts));
		results.put("midMin", "" + StatUtils.min(midAmounts));
		results.put("midMax", "" + StatUtils.max(midAmounts));

		return results;
	}

	@Override
	public Map<String, String> evalFrameworks(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		// results.putAll(data.entrySet().stream().collect(Collectors.toMap(e ->
		// e.getKey(), e -> "" + e.getValue())));

		Map<String, Long> frameworkMap = new HashMap<String, Long>();
		Map<String, Long> runtimeMap = new HashMap<String, Long>();

		data.entrySet().forEach(entry -> {
			int indexOfFirstMarker = entry.getKey().indexOf("|");
			int indexOfSecondMarker = entry.getKey().indexOf("|", indexOfFirstMarker + 1);
			String framework = entry.getKey().substring(0, entry.getKey().indexOf("|"));
			String runtime = entry.getKey().substring(indexOfFirstMarker + 1, indexOfSecondMarker);
			long value = entry.getValue();
			if (frameworkMap.putIfAbsent(framework, value) != null) {
				frameworkMap.replace(framework, frameworkMap.get(framework) + value);
			}
			if (runtimeMap.putIfAbsent(runtime, value) != null) {
				runtimeMap.replace(runtime, runtimeMap.get(runtime) + value);
			}
		});

		results = frameworkMap.entrySet().stream()
				.collect(Collectors.toMap(e -> "fra|" + e.getKey(), e -> "" + e.getValue()));
		results.putAll(runtimeMap.entrySet().stream()
				.collect(Collectors.toMap(e -> "run|" + e.getKey(), e -> "" + e.getValue())));

		double[] fraAmounts = frameworkMap.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		results.put("fraMean", "" + StatUtils.mean(fraAmounts));
		results.put("fraVar", "" + StatUtils.populationVariance(fraAmounts));
		results.put("fraStdev", "" + Math.sqrt(Double.parseDouble(results.get("fraVar"))));
		results.put("fraStdev", "" + new Median().evaluate(fraAmounts));
		results.put("fraMin", "" + StatUtils.min(fraAmounts));
		results.put("fraMax", "" + StatUtils.max(fraAmounts));

		double[] runAmounts = runtimeMap.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		results.put("runMean", "" + StatUtils.mean(runAmounts));
		results.put("runVar", "" + StatUtils.populationVariance(runAmounts));
		results.put("runStdev", "" + Math.sqrt(Double.parseDouble(results.get("runVar"))));
		results.put("runStdev", "" + new Median().evaluate(runAmounts));
		results.put("runMin", "" + StatUtils.min(runAmounts));
		results.put("runMax", "" + StatUtils.max(runAmounts));

		return results;
	}

	@Override
	public Map<String, String> evalServices(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		Map<String, Long> serviceMap = new HashMap<String, Long>();
		Map<String, Long> typeMap = new HashMap<String, Long>();

		data.entrySet().forEach(entry -> {
			int indexOfFirstMarker = entry.getKey().indexOf("|");
			int indexOfSecondMarker = entry.getKey().indexOf("|", indexOfFirstMarker + 1);
			String service = entry.getKey().substring(0, entry.getKey().indexOf("|"));
			String type = entry.getKey().substring(indexOfFirstMarker + 1, indexOfSecondMarker);
			long value = entry.getValue();
			if (serviceMap.putIfAbsent(service, value) != null) {
				serviceMap.replace(service, serviceMap.get(service) + value);
			}
			if (typeMap.putIfAbsent(type, value) != null) {
				typeMap.replace(type, typeMap.get(type) + value);
			}
		});

		results = serviceMap.entrySet().stream()
				.collect(Collectors.toMap(e -> "ser|" + e.getKey(), e -> "" + e.getValue()));
		results.putAll(typeMap.entrySet().stream()
				.collect(Collectors.toMap(e -> "typ|" + e.getKey(), e -> "" + e.getValue())));

		double[] serAmounts = serviceMap.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		results.put("serMean", "" + StatUtils.mean(serAmounts));
		results.put("serVar", "" + StatUtils.populationVariance(serAmounts));
		results.put("serStdev", "" + Math.sqrt(Double.parseDouble(results.get("serVar"))));
		results.put("serStdev", "" + new Median().evaluate(serAmounts));
		results.put("serMin", "" + StatUtils.min(serAmounts));
		results.put("serMax", "" + StatUtils.max(serAmounts));

		double[] typAmounts = typeMap.entrySet().stream().mapToDouble(e -> e.getValue().doubleValue()).toArray();
		results.put("typMean", "" + StatUtils.mean(typAmounts));
		results.put("typVar", "" + StatUtils.populationVariance(typAmounts));
		results.put("typStdev", "" + Math.sqrt(Double.parseDouble(results.get("typVar"))));
		results.put("typStdev", "" + new Median().evaluate(typAmounts));
		results.put("typMin", "" + StatUtils.min(typAmounts));
		results.put("typMax", "" + StatUtils.max(typAmounts));

		return results;
	}

	@Override
	public Map<String, String> evalExtensible(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		results.putAll(data.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> "" + e.getValue())));

		results.putAll(getQualitativeMode(data));

		return results;
	}

	@Override
	public Map<String, String> evalInfrastructures(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		Map<String, Long> continentMap = new HashMap<String, Long>();
		Map<String, Long> countryMap = new HashMap<String, Long>();
		Map<String, Long> regionMap = new HashMap<String, Long>();
		Map<String, Long> providerMap = new HashMap<String, Long>();

		data.entrySet().forEach(entry -> {
			int indexOfFirstMarker = entry.getKey().indexOf("|");
			int indexOfSecondMarker = entry.getKey().indexOf("|", indexOfFirstMarker + 1);
			int indexOfThirdMarker = entry.getKey().indexOf("|", indexOfSecondMarker + 1);

			String continent = entry.getKey().substring(0, indexOfFirstMarker);
			String country = entry.getKey().substring(indexOfFirstMarker + 1, indexOfSecondMarker);
			String region = entry.getKey().substring(indexOfSecondMarker + 1, indexOfThirdMarker);
			String provider = entry.getKey().substring(indexOfThirdMarker + 1);

			// Eval Continent
			if (!continent.isEmpty()) {
				continentMap.put(continent, entry.getValue());
			}

			// Eval Country
			if (!country.isEmpty()) {
				countryMap.put(country, entry.getValue());
			}

			// Eval Region
			if (!region.isEmpty()) {
				regionMap.put(region, entry.getValue());
			}

			// Eval Provider
			if (!provider.isEmpty()) {
				providerMap.put(provider, entry.getValue());
			}
		});

		results.put("continentSize", "" + continentMap.size());
		results.putAll(continentMap.entrySet().stream()
				.collect(Collectors.toMap(e -> "continent|" + e.getKey(), e -> "" + e.getValue())));

		results.put("countrySize", "" + countryMap.size());
		results.putAll(countryMap.entrySet().stream()
				.collect(Collectors.toMap(e -> "country|" + e.getKey(), e -> "" + e.getValue())));

		results.put("regionSize", "" + regionMap.size());
		results.putAll(regionMap.entrySet().stream()
				.collect(Collectors.toMap(e -> "region|" + e.getKey(), e -> "" + e.getValue())));

		results.put("providerSize", "" + providerMap.size());
		results.putAll(providerMap.entrySet().stream()
				.collect(Collectors.toMap(e -> "provider|" + e.getKey(), e -> "" + e.getValue())));

		// Top 5
//		continentMap.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(5)
//				.collect(Collectors.toList());

		return results;
	}

	private Map<String, String> getQualitativeMode(Map<String, Long> data) {
		Map<String, String> results = new HashMap<String, String>();

		long modeCount = 1;
		results.put("mode" + (modeCount - 1), "|" + (long) 0);

		for (Entry<String, Long> entry : data.entrySet()) {

			// Gets the current mode from the results map and puts its value
			// with the name into a variable
			long currentMode = Long.parseLong(results.get("mode" + (modeCount - 1))
					.substring(results.get("mode" + (modeCount - 1)).indexOf("|") + 1));

			if (!entry.getKey().equals("null")) {

				if (entry.getValue() > currentMode) {

					// Falls es nur einen Modus gibt
					if (modeCount == 1) {
						results.replace("mode" + (modeCount - 1), results.get("mode" + (modeCount - 1)),
								entry.getKey() + "|" + entry.getValue());
					} else if (modeCount > 1) {
						// Falls es mehr als einen Modus gibt
						for (; modeCount > 1; modeCount--) {
							// Lösche alle Modi um wieder bei mode0 zu beginnen
							results.remove("mode" + (modeCount - 1));
						}
						// Setze anschließend wieder mode0
						results.put("mode" + (modeCount - 1), entry.getKey() + "|" + entry.getValue());
					}
				} else if (entry.getValue() == currentMode) {
					// Add another mode
					modeCount++;
					results.put("mode" + (modeCount - 1), entry.getKey() + "|" + entry.getValue());
				}

			}
		}
		results.put("modeCount", "" + modeCount);
		return results;
	}

}
