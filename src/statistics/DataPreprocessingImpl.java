package statistics;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.PaasProfile;
import models.PricingModel;
import models.PricingPeriod;

public class DataPreprocessingImpl implements DataPreprocessing {

	List<PaasProfile> complianceProfiles = new LinkedList<PaasProfile>();

	public DataPreprocessingImpl() {

	}

	public Map<String, Long> evalRevision(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		int i = 0;
		for (PaasProfile profile : profiles) {
			long revisionAge = 0;
			try {
				// The first 10 chars of the revision is always only the date
				revisionAge = ChronoUnit.DAYS.between(LocalDate.parse(profile.getRevision().substring(0, 10)),
						LocalDate.now());
				results.putIfAbsent("revision" + i, revisionAge);
				i++;
			} catch (DateTimeParseException e) {
				System.out.println(e.getMessage());
			}
		}
		return results;
	}

	public Map<String, Long> evalStatus(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("size", (long) profiles.size());
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

	public Map<String, Long> evalStatusSince(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		int i = 0;
		long statusSince = 0;
		for (PaasProfile profile : profiles) {
			if (!profile.getStatusSince().contains("null")) {
				try {
					// The first 10 chars of the revision is always only the
					// date
					statusSince = ChronoUnit.DAYS.between(LocalDate.parse(profile.getStatusSince().substring(0, 10)),
							LocalDate.now());
					results.putIfAbsent("statusSince" + i, statusSince);
					i++;
				} catch (DateTimeParseException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		results.put("size", (long) (i + 1));
		return results;
	}

	public Map<String, Long> evalType(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("size", (long) profiles.size());
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

	@Override
	public Map<String, Long> evalPlatform(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			if (!results.containsKey(profile.getPlatform())) {
				results.put(profile.getPlatform(), (long) 1);
			} else {
				results.replace(profile.getPlatform(), results.get(profile.getPlatform()),
						results.get(profile.getPlatform()) + 1);
			}
		});
		return results;
	}

	public Map<String, Long> evalHosting(List<PaasProfile> profiles) {
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

	public Map<String, Long> evalPricing(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("modelcounter0", (long) 0);
		results.put("modelcounter1", (long) 0);
		results.put("modelcounter2", (long) 0);
		results.put("modelcounter3", (long) 0);
		results.put("modelcounter4", (long) 0);
		results.put("modelfree", (long) 0);
		results.put("modelfixed", (long) 0);
		results.put("modelmetered", (long) 0);
		results.put("modelhybrid", (long) 0);
		results.put("modelempty", (long) 0);

		results.put("perioddaily", (long) 0);
		results.put("periodmonthly", (long) 0);
		results.put("periodanually", (long) 0);
		results.put("periodempty", (long) 0);

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
					results.replace("modelfree", results.get("modelfree"), results.get("modelfree") + 1);
				} else if (pricing.getModel().equals(PricingModel.fixed)) {
					results.replace("modelfixed", results.get("modelfixed"), results.get("modelfixed") + 1);
				} else if (pricing.getModel().equals(PricingModel.metered)) {
					results.replace("modelmetered", results.get("modelmetered"), results.get("modelmetered") + 1);
				} else if (pricing.getModel().equals(PricingModel.hybrid)) {
					results.replace("modelhybrid", results.get("modelhybrid"), results.get("modelhybrid") + 1);
				} else if (pricing.getModel().equals(PricingModel.empty)) {
					results.replace("modelempty", results.get("modelempty"), results.get("modelempty") + 1);
				}

				if (pricing.getPeriod().equals(PricingPeriod.daily)) {
					results.replace("perioddaily", results.get("perioddaily"), results.get("perioddaily") + 1);
				} else if (pricing.getPeriod().equals(PricingPeriod.monthly)) {
					results.replace("periodmonthly", results.get("periodmonthly"), results.get("periodmonthly") + 1);
				} else if (pricing.getPeriod().equals(PricingPeriod.anually)) {
					results.replace("periodanually", results.get("periodanually"), results.get("periodanually") + 1);
				} else if (pricing.getPeriod().equals(PricingPeriod.empty)) {
					results.replace("periodempty", results.get("periodempty"), results.get("periodempty") + 1);
				}
			});
		});
		return results;
	}

	public Map<String, Long> evalScaling(List<PaasProfile> profiles) {
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

	public Map<String, Long> evalRuntimes(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();

		// Iterates through profiles and puts the each version of each language
		// for each profile/runtime into results or increments if the version of
		// the language is already contained. The Format is
		// ("language"|"version")
		profiles.forEach(profile -> {
			profile.getRuntimes().forEach(runtime -> {
				runtime.getVersions().forEach(version -> {
					String key = runtime.getLanguage() + "|" + version;
					if (results.putIfAbsent(key, (long) 1) != null) {
						results.replace(key, results.get(key), results.get(key) + 1);
					}
				});
			});
		});

		// results.keySet().forEach(key -> {
		// if (key.startsWith("apex"))
		// results.get(key);
		// });

		return results;
	}

	@Override
	public Map<String, Long> evalMiddleware(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getMiddlewares().forEach(middleware -> {
				middleware.getVersions().forEach(version -> {
					String key = middleware.getName() + "|" + version;
					if (results.putIfAbsent(key, (long) 1) != null) {
						results.replace(key, results.get(key), results.get(key) + 1);
					}
				});
			});
		});
		return results;
	}

	@Override
	public Map<String, Long> evalFrameworks(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getFrameworks().forEach(framework -> {
				framework.getVersions().forEach(version -> {
					String key = framework.getName() + "|" + framework.getRuntime() + "|" + version;
					if (results.putIfAbsent(key, (long) 1) != null) {
						results.replace(key, results.get(key), results.get(key) + 1);
					}
				});
			});
		});
		return results;
	}

	@Override
	public Map<String, Long> evalServices(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getServices().getNative().forEach(nativeService -> {
				nativeService.getVersions().forEach(version -> {
					String key = nativeService.getName() + "|" + nativeService.getType() + "|" + version;
					if (results.putIfAbsent(key, (long) 1) != null) {
						results.replace(key, results.get(key), results.get(key) + 1);
					}
				});
			});
		});
		return results;
	}

	@Override
	public Map<String, Long> evalExtensible(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		results.put("true", (long) 0);
		results.put("false", (long) 0);
		results.put("unknown", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getExtensible().equals("true")) {
				results.replace("true", results.get("true"), results.get("true") + 1);
			}
			if (profile.getExtensible().equals("false")) {
				results.replace("false", results.get("false"), results.get("false") + 1);
			}
			if (profile.getExtensible().equals("unknown")) {
				results.replace("unknown", results.get("unknown"), results.get("unknown") + 1);
			}
		});
		return results;
	}

	@Override
	public Map<String, Long> evalInfrastructures(List<PaasProfile> profiles) {
		Map<String, Long> results = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getInfrastructures().forEach(infrastructure -> {
				String key = infrastructure.getContinent() + "|" + infrastructure.getCountry() + "|"
						+ infrastructure.getRegion() + "|" + infrastructure.getProvider();
				if (results.putIfAbsent(key, (long) 1) != null) {
					results.replace(key, results.get(key), results.get(key) + 1);
				}
			});
		});
		return results;
	}

	@Override
	public Map<String, Double> evalQos(List<PaasProfile> profiles) {
		Map<String, Double> results = new HashMap<String, Double>();

		int i = 0;
		for (PaasProfile profile : profiles) {
			// Count Qos in profiles
			double uptime = profile.getQos().getUptime();
			if (!Double.isNaN(uptime)) {
				results.put("qos" + i, uptime);
				i++;
			}

			// Check if List of Compliances is empty else continue computation
			if (!profile.getQos().getCompliance().isEmpty()) {
				complianceProfiles.add(profile);
			}
		}
		return results;
	}

	public Map<String, Long> evalCompliance() {
		Map<String, Long> results = new HashMap<String, Long>();

		results.put("size", (long) complianceProfiles.size());

		for (PaasProfile profile : complianceProfiles) {

			// "#c-" Counter for number of compliances in this profile
			results.put("#c-" + profile.getName(), (long) 0);
			for (String compliance : profile.getQos().getCompliance()) {
				results.replace("#c-" + profile.getName(), results.get("#c-" + profile.getName()),
						results.get("#c-" + profile.getName()) + 1);

				if (results.putIfAbsent("comp|" + compliance, (long) 1) != null) {
					// Count number of appereances of this compliance in all
					// profiles
					results.replace("comp|" + compliance, results.get("comp|" + compliance),
							results.get("comp|" + compliance) + 1);
				}
			}
		}
		return results;
	}
}
