package statistics;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import models.PaasProfile;
import models.PricingModel;
import models.PricingPeriod;

public class Statistics {

	DescriptiveStatistics stats;

	public Statistics() {
		stats = new DescriptiveStatistics();
	}

	public static Map<String, Long> evalRevision(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("latest", (long) 0);
		results.put("oldest", (long) 0);
		results.put("mean", (long) 0);
		profiles.forEach(profile -> {
			long revisionAge = 0;
			try {
				revisionAge = ChronoUnit.DAYS.between(LocalDate.parse(profile.getRevision().substring(0, 10)),
						LocalDate.now());
				if (revisionAge != 0 && revisionAge < results.get("latest")) {
					results.replace("latest", results.get("latest"), revisionAge);
				}
				if (revisionAge != 0 && revisionAge > results.get("oldest")) {
					results.replace("oldest", results.get("oldest"), revisionAge);
				}
				results.replace("mean", results.get("mean"), results.get("mean") + revisionAge);
			} catch (DateTimeParseException e) {
				System.out.println("x");
				System.out.println(e.getMessage());
			}
		});
		results.replace("mean", results.get("mean"), results.get("mean") / profiles.size());
		return results;
	}

	public static Map<String, Long> evalStatus(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("production", (long) 0);
		results.put("alpha", (long) 0);
		results.put("beta", (long) 0);
		profiles.forEach(profile -> {
			if (profile.getStatus().equalsIgnoreCase("production")) {
				results.replace("production", results.get("production"), results.get("production") + 1);
			} else if (profile.getStatus().equalsIgnoreCase("alpha")) {
				results.replace("alpha", results.get("alpha"), results.get("alpha") + 1);
			} else if (profile.getStatus().equalsIgnoreCase("beta")) {
				results.replace("beta", results.get("beta"), results.get("beta") + 1);
			}
		});
		return results;
	}

	public static Map<String, Double> evalStatusSince(List<PaasProfile> profiles) {
		Map<String, Double> results = new HashMap<String, Double>();
		results.put("latest", Double.MAX_VALUE);
		results.put("oldest", Double.MIN_VALUE);
		results.put("mean", 0.0);

		profiles.forEach(profile -> {
			double statusSinceAge = 0;
			if (!profile.getStatusSince().equalsIgnoreCase("null")) {
				try {
					statusSinceAge = ChronoUnit.DAYS.between(LocalDate.parse(profile.getStatusSince().substring(0, 10)),
							LocalDate.now());
					if (statusSinceAge != 0 && statusSinceAge < results.get("latest")) {
						results.replace("latest", results.get("latest"), statusSinceAge);
					}
					if (statusSinceAge != 0 && statusSinceAge > results.get("oldest")) {
						results.replace("oldest", results.get("oldest"), statusSinceAge);
					}
					results.replace("mean", results.get("mean"), results.get("mean") + statusSinceAge);
				} catch (DateTimeParseException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		results.replace("mean", results.get("mean"), results.get("mean") / profiles.size());
		return results;
	}

	public static Map<String, Long> evalType(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("SaaS-centric", (long) 0);
		results.put("Generic", (long) 0);
		results.put("IaaS-centric", (long) 0);
		profiles.forEach(profile -> {
			if (profile.getType().equalsIgnoreCase("SaaS-centric")) {
				results.replace("SaaS-centric", results.get("SaaS-centric"), results.get("SaaS-centric") + 1);
			} else if (profile.getType().equalsIgnoreCase("Generic")) {
				results.replace("Generic", results.get("Generic"), results.get("Generic") + 1);
			} else if (profile.getType().equalsIgnoreCase("IaaS-centric")) {
				results.replace("IaaS-centric", results.get("IaaS-centric"), results.get("IaaS-centric") + 1);
			}
		});
		return results;
	}

	public static Map<String, Long> evalHosting(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("public", (long) 0);
		results.put("private", (long) 0);
		results.put("virtual_private", (long) 0);
		profiles.forEach(profile -> {
			if (profile.getHosting().getPublic()) {
				results.replace("public", results.get("public"), results.get("public") + 1);
			} else if (profile.getHosting().getPrivate()) {
				results.replace("private", results.get("private"), results.get("private") + 1);
			} else if (profile.getHosting().getVirtualPrivate()) {
				results.replace("virtual_private", results.get("virtual_private"), results.get("virtual_private") + 1);
			}
		});
		return results;
	}

	public static Map<String, Long> evalPricing(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("modelcounter0", (long) 0);
		results.put("modelcounter1", (long) 0);
		results.put("modelcounter2", (long) 0);
		results.put("modelcounter3", (long) 0);
		results.put("modelcounter4", (long) 0);
		results.put("free", (long) 0);
		results.put("fixed", (long) 0);
		results.put("metered", (long) 0);
		results.put("hybrid", (long) 0);
		results.put("model-empty", (long) 0);
		results.put("daily", (long) 0);
		results.put("monthly", (long) 0);
		results.put("anually", (long) 0);
		results.put("period-empty", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getPricings().size() == 0) {
				results.replace("modelcounter0", results.get("modelcounter0"), results.get("modelcounter0") + 1);
			} else if (profile.getPricings().size() == 1) {
				results.replace("modelcounter1", results.get("modelcounter1"), results.get("modelcounter1") + 1);
			} else if (profile.getPricings().size() == 2) {
				results.replace("modelcounter2", results.get("modelcounter2"), results.get("modelcounter2") + 1);
			} else if (profile.getPricings().size() == 3) {
				results.replace("modelcounter3", results.get("modelcounter3"), results.get("modelcounter3") + 1);
			} else if (profile.getPricings().size() == 4) {
				results.replace("modelcounter4", results.get("modelcounter4"), results.get("modelcounter4") + 1);
			}
			profile.getPricings().forEach(pricing -> {
				if (pricing.getModel().equals(PricingModel.free)) {
					results.replace("free", results.get("free"), results.get("free") + 1);
				} else if (pricing.getModel().equals(PricingModel.fixed)) {
					results.replace("fixed", results.get("fixed"), results.get("fixed") + 1);
				} else if (pricing.getModel().equals(PricingModel.metered)) {
					results.replace("metered", results.get("metered"), results.get("metered") + 1);
				} else if (pricing.getModel().equals(PricingModel.hybrid)) {
					results.replace("hybrid", results.get("hybrid"), results.get("hybrid") + 1);
				} else if (pricing.getModel().equals(PricingModel.empty)) {
					results.replace("model-empty", results.get("model-empty"), results.get("model-empty") + 1);
				}

				if (pricing.getPeriod().equals(PricingPeriod.daily)) {
					results.replace("daily", results.get("daily"), results.get("daily") + 1);
				} else if (pricing.getPeriod().equals(PricingPeriod.monthly)) {
					results.replace("monthly", results.get("monthly"), results.get("monthly") + 1);
				} else if (pricing.getPeriod().equals(PricingPeriod.anually)) {
					results.replace("anually", results.get("anually"), results.get("anually") + 1);
				} else if (pricing.getPeriod().equals(PricingPeriod.empty)) {
					results.replace("period-empty", results.get("period-empty"), results.get("period-empty") + 1);
				}
			});
		});
		return results;
	}

	public static Map<String, Long> evalScaling(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("vertical", (long) 0);
		results.put("horizontal", (long) 0);
		results.put("auto", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getScaling().getVertical()) {
				results.replace("vertical", results.get("vertical"), results.get("vertical") + 1);
			}
			if (profile.getScaling().getHorizontal()) {
				results.replace("horizontal", results.get("horizontal"), results.get("horizontal") + 1);
			}
			if (profile.getScaling().getAuto()) {
				results.replace("auto", results.get("auto"), results.get("auto") + 1);
			}
		});
		return results;
	}

	public static Map<String, Long> evalRuntimes(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("apex", (long) 0);
		results.put("clojure", (long) 0);
		results.put("cobol", (long) 0);
		results.put("dotnet", (long) 0);
		results.put("erlang", (long) 0);
		results.put("go", (long) 0);
		results.put("groovy", (long) 0);
		results.put("haskell", (long) 0);
		results.put("java", (long) 0);
		results.put("lua", (long) 0);
		results.put("node", (long) 0);
		results.put("perl", (long) 0);
		results.put("php", (long) 0);
		results.put("python", (long) 0);
		results.put("ruby", (long) 0);
		results.put("scala", (long) 0);

		profiles.forEach(profile -> {
			profile.getRuntimes().forEach(runtime -> {
				if (runtime.getLanguage().equalsIgnoreCase("apex")) {
					results.replace("apex", results.get("apex"), results.get("apex") + 1);
					runtime.getVersions().forEach(version -> {
						if (!results.containsKey("apex-version" + version)) {
							results.replace("apex-version" + version, results.get("apex-version" + version),
									results.get("apex-version" + version) + 1);
						} else {
							results.put("apex-version" + version, (long) 1);
						}
					});
				} else if (runtime.getLanguage().equalsIgnoreCase("clojure")) {
					results.replace("clojure", results.get("clojure"), results.get("clojure") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("cobol")) {
					results.replace("cobol", results.get("cobol"), results.get("cobol") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("dotnet")) {
					results.replace("dotnet", results.get("dotnet"), results.get("dotnet") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("erlang")) {
					results.replace("erlang", results.get("erlang"), results.get("erlang") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("go")) {
					results.replace("go", results.get("go"), results.get("go") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("groovy")) {
					results.replace("groovy", results.get("groovy"), results.get("groovy") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("haskell")) {
					results.replace("haskell", results.get("haskell"), results.get("haskell") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("java")) {
					results.replace("java", results.get("java"), results.get("java") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("lua")) {
					results.replace("lua", results.get("lua"), results.get("lua") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("node")) {
					results.replace("node", results.get("node"), results.get("node") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("perl")) {
					results.replace("perl", results.get("perl"), results.get("perl") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("php")) {
					results.replace("php", results.get("php"), results.get("php") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("python")) {
					results.replace("python", results.get("python"), results.get("python") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("ruby")) {
					results.replace("ruby", results.get("ruby"), results.get("ruby") + 1);
				} else if (runtime.getLanguage().equalsIgnoreCase("scala")) {
					results.replace("scala", results.get("scala"), results.get("scala") + 1);
				}
			});
		});

		return null;
	}

}
