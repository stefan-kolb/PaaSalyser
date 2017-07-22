package statistics;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import profile.PaasProfile;
import profile.models.Middleware;
import profile.models.PricingModel;
import profile.models.PricingPeriod;
import profile.models.Runtime;

public class DataPreprocessing {

	private List<PaasProfile> profiles = new ArrayList<PaasProfile>();

	private Map<String, Long> revision;
	private Map<String, Long> status;
	private Map<String, Long> statusSince;
	private Map<String, Long> type;
	private Map<String, Double> qos;
	private Map<String, Long> compliance;
	private Map<String, Long> platform;
	private Map<String, Long> hosting;
	private Map<String, Long> pricing;
	private Map<String, Long> scaling;
	private Map<String, Long> runtimes;
	private Map<String, Long> middleware;
	private Map<String, Long> frameworks;
	private Map<String, Long> services;
	private Map<String, Long> extensible;
	private Map<String, Long> infrastructures;

	public DataPreprocessing(List<PaasProfile> profiles) {
		this.profiles = profiles;

		// Execute all evaluations
		evalRevision();
		evalStatus();
		evalStatusSince();
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
		evalQos();
		evalCompliance();
	}

	public List<PaasProfile> getProfiles() {
		return profiles;
	}

	public Map<String, Long> getRevision() {
		return revision;
	}

	public Map<String, Long> getStatus() {
		return status;
	}

	public Map<String, Long> getStatusSince() {
		return statusSince;
	}

	public Map<String, Long> getType() {
		return type;
	}

	public Map<String, Double> getQos() {
		return qos;
	}

	public Map<String, Long> getCompliance() {
		return compliance;
	}

	public Map<String, Long> getPlatform() {
		return platform;
	}

	public Map<String, Long> getHosting() {
		return hosting;
	}

	public Map<String, Long> getPricing() {
		return pricing;
	}

	public Map<String, Long> getScaling() {
		return scaling;
	}

	public Map<String, Long> getRuntimes() {
		return runtimes;
	}

	public Map<String, Long> getMiddleware() {
		return middleware;
	}

	public Map<String, Long> getFrameworks() {
		return frameworks;
	}

	public Map<String, Long> getServices() {
		return services;
	}

	public Map<String, Long> getExtensible() {
		return extensible;
	}

	public Map<String, Long> getInfrastructures() {
		return infrastructures;
	}

	private void evalRevision() {
		revision = new HashMap<String, Long>();
		for (PaasProfile profile : profiles) {
			long revisionAge = 0;
			try {
				// The first 10 chars of the revision is always only the date
				revisionAge = ChronoUnit.DAYS.between(LocalDate.parse(profile.getRevision().substring(0, 10)),
						LocalDate.now());
				revision.putIfAbsent(profile.getName(), revisionAge);
			} catch (DateTimeParseException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void evalStatus() {
		status = new HashMap<String, Long>();
		status.put("size", (long) profiles.size());
		status.put("production", (long) 0);
		status.put("alpha", (long) 0);
		status.put("beta", (long) 0);
		status.put("eol", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getStatus().equalsIgnoreCase("production")) {
				status.replace("production", status.get("production"), status.get("production") + 1);
			} else if (profile.getStatus().equalsIgnoreCase("alpha")) {
				status.replace("alpha", status.get("alpha"), status.get("alpha") + 1);
			} else if (profile.getStatus().equalsIgnoreCase("beta")) {
				status.replace("beta", status.get("beta"), status.get("beta") + 1);
			} else if (profile.getStatus().equalsIgnoreCase("eol")) {
				status.replace("eol", status.get("eol"), status.get("eol") + 1);
			}
		});
	}

	private void evalStatusSince() {
		statusSince = new HashMap<String, Long>();
		int i = 0;
		long statusSinceDays = 0;
		for (PaasProfile profile : profiles) {
			if (!(profile.getStatusSince().contains("null") || profile.getStatusSince().contains(""))) {
				try {
					// The first 10 chars of the revision is always only the
					// date
					statusSinceDays = ChronoUnit.DAYS
							.between(LocalDate.parse(profile.getStatusSince().substring(0, 10)), LocalDate.now());
					statusSince.putIfAbsent("statusSince" + i, statusSinceDays);
				} catch (DateTimeParseException e) {
					System.out.println(e.getMessage());
				}
			}
			i++;
		}
		statusSince.put("size", (long) (i + 1));
	}

	private void evalType() {
		type = new HashMap<String, Long>();
		type.put("size", (long) profiles.size());
		type.put("SaaS-centric", (long) 0);
		type.put("Generic", (long) 0);
		type.put("IaaS-centric", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getType().equalsIgnoreCase("SaaS-centric")) {
				type.replace("SaaS-centric", type.get("SaaS-centric"), type.get("SaaS-centric") + 1);
			} else if (profile.getType().equalsIgnoreCase("Generic")) {
				type.replace("Generic", type.get("Generic"), type.get("Generic") + 1);
			} else if (profile.getType().equalsIgnoreCase("IaaS-centric")) {
				type.replace("IaaS-centric", type.get("IaaS-centric"), type.get("IaaS-centric") + 1);
			}
		});
	}

	private void evalPlatform() {
		platform = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			if (!platform.containsKey(profile.getPlatform())) {
				if (profile.getPlatform().equals("null")) {
					platform.put("No Platform", (long) 1);
				} else {
					platform.put(profile.getPlatform(), (long) 1);
				}
			} else {
				if (profile.getPlatform().equals("null")) {
					platform.put("No Platform", (long) 1);
				} else {
					platform.put(profile.getPlatform(), (long) 1);
				}
				platform.replace(profile.getPlatform(), platform.get(profile.getPlatform()),
						platform.get(profile.getPlatform()) + 1);
			}
		});
	}

	public void evalHosting() {
		hosting = new HashMap<String, Long>();
		hosting.put("public", (long) 0);
		hosting.put("private", (long) 0);
		hosting.put("virtual_private", (long) 0);
		profiles.forEach(profile -> {
			if (profile.getHosting().getPublic()) {
				hosting.replace("public", hosting.get("public"), hosting.get("public") + 1);
			}
			if (profile.getHosting().getPrivate()) {
				hosting.replace("private", hosting.get("private"), hosting.get("private") + 1);
			}
			if (profile.getHosting().getVirtualPrivate()) {
				hosting.replace("virtual_private", hosting.get("virtual_private"), hosting.get("virtual_private") + 1);
			} 
		});
	}

	private void evalPricing() {
		pricing = new HashMap<String, Long>();
		pricing.put("modelcounter0", (long) 0);
		pricing.put("modelcounter1", (long) 0);
		pricing.put("modelcounter2", (long) 0);
		pricing.put("modelcounter3", (long) 0);
		pricing.put("modelcounter4", (long) 0);
		pricing.put("modelfree", (long) 0);
		pricing.put("modelfixed", (long) 0);
		pricing.put("modelmetered", (long) 0);
		pricing.put("modelhybrid", (long) 0);
		pricing.put("modelempty", (long) 0);

		pricing.put("perioddaily", (long) 0);
		pricing.put("periodmonthly", (long) 0);
		pricing.put("periodanually", (long) 0);
		pricing.put("periodempty", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getPricings().size() == 0) {
				pricing.replace("modelcounter0", pricing.get("modelcounter0"), pricing.get("modelcounter0") + 1);
			} else if (profile.getPricings().size() == 1) {
				pricing.replace("modelcounter1", pricing.get("modelcounter1"), pricing.get("modelcounter1") + 1);
			} else if (profile.getPricings().size() == 2) {
				pricing.replace("modelcounter2", pricing.get("modelcounter2"), pricing.get("modelcounter2") + 1);
			} else if (profile.getPricings().size() == 3) {
				pricing.replace("modelcounter3", pricing.get("modelcounter3"), pricing.get("modelcounter3") + 1);
			} else if (profile.getPricings().size() == 4) {
				pricing.replace("modelcounter4", pricing.get("modelcounter4"), pricing.get("modelcounter4") + 1);
			}
			profile.getPricings().forEach(profilePricing -> {
				if (profilePricing.getModel().equals(PricingModel.free)) {
					pricing.replace("modelfree", pricing.get("modelfree"), pricing.get("modelfree") + 1);
				} else if (profilePricing.getModel().equals(PricingModel.fixed)) {
					pricing.replace("modelfixed", pricing.get("modelfixed"), pricing.get("modelfixed") + 1);
				} else if (profilePricing.getModel().equals(PricingModel.metered)) {
					pricing.replace("modelmetered", pricing.get("modelmetered"), pricing.get("modelmetered") + 1);
				} else if (profilePricing.getModel().equals(PricingModel.hybrid)) {
					pricing.replace("modelhybrid", pricing.get("modelhybrid"), pricing.get("modelhybrid") + 1);
				} else if (profilePricing.getModel().equals(PricingModel.empty)) {
					pricing.replace("modelempty", pricing.get("modelempty"), pricing.get("modelempty") + 1);
				}

				if (profilePricing.getPeriod().equals(PricingPeriod.daily)) {
					pricing.replace("perioddaily", pricing.get("perioddaily"), pricing.get("perioddaily") + 1);
				} else if (profilePricing.getPeriod().equals(PricingPeriod.monthly)) {
					pricing.replace("periodmonthly", pricing.get("periodmonthly"), pricing.get("periodmonthly") + 1);
				} else if (profilePricing.getPeriod().equals(PricingPeriod.anually)) {
					pricing.replace("periodanually", pricing.get("periodanually"), pricing.get("periodanually") + 1);
				} else if (profilePricing.getPeriod().equals(PricingPeriod.empty)) {
					pricing.replace("periodempty", pricing.get("periodempty"), pricing.get("periodempty") + 1);
				}
			});
		});
	}

	private void evalScaling() {
		scaling = new HashMap<String, Long>();
		scaling.put("vertical", (long) 0);
		scaling.put("horizontal", (long) 0);
		scaling.put("auto", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getScaling().getVertical()) {
				scaling.replace("vertical", scaling.get("vertical"), scaling.get("vertical") + 1);
			}
			if (profile.getScaling().getHorizontal()) {
				scaling.replace("horizontal", scaling.get("horizontal"), scaling.get("horizontal") + 1);
			}
			if (profile.getScaling().getAuto()) {
				scaling.replace("auto", scaling.get("auto"), scaling.get("auto") + 1);
			}
		});
	}

	private void evalRuntimes() {
		runtimes = new HashMap<String, Long>();
		Map<String, Long> runtimeAmounts = new HashMap<String, Long>();
		Map<String, Long> versionAmounts = new HashMap<String, Long>();

		long maxAmountRuntimes = 0;
		long maxAmountVersions = 0;

		// Iterates through profiles and puts the each version of each language
		// for each profile/runtime into results or increments if the version of
		// the language is already contained. The Format is
		// ("language"|"version")
		for (PaasProfile profile : profiles) {
			long numberOfRuntimes = profile.getRuntimes().size();

			// Count number of runtimes per profile
			if (numberOfRuntimes > maxAmountRuntimes) {
				maxAmountRuntimes = numberOfRuntimes;
			}
			if (runtimeAmounts.containsKey("runAmount|" + numberOfRuntimes)) {
				runtimeAmounts.replace("runAmount|" + numberOfRuntimes,
						runtimeAmounts.get("runAmount|" + numberOfRuntimes) + 1);
			} else {
				runtimeAmounts.put("runAmount|" + numberOfRuntimes, (long) 1);
			}
			for (Runtime runtime : profile.getRuntimes()) {
				// Begin Version Evaluation
				long numberOfVersions = runtime.getVersions().size();

				// Count number of runtimes per profile
				if (numberOfVersions > maxAmountVersions) {
					maxAmountVersions = numberOfVersions;
				}

				if (versionAmounts.containsKey("verAmount|" + numberOfVersions)) {
					versionAmounts.replace("verAmount|" + numberOfVersions,
							versionAmounts.get("verAmount|" + numberOfVersions) + 1);
				} else {
					versionAmounts.put("verAmount|" + numberOfVersions, (long) 1);
				}

				for (String version : runtime.getVersions()) {
					String key = runtime.getLanguage() + "|" + version;
					if (runtimes.putIfAbsent(key, (long) 1) != null) {
						runtimes.replace(key, runtimes.get(key), runtimes.get(key) + 1);
					}
				}
			}
		}

		runtimes.putAll(runtimeAmounts);
		runtimes.putAll(versionAmounts);
		runtimes.put("maxRuntimes", maxAmountRuntimes);
		runtimes.put("maxVersions", maxAmountVersions);
	}

	private void evalMiddleware() {
		middleware = new HashMap<String, Long>();

		Map<String, Long> middlewares = new HashMap<String, Long>();

		long maxAmount = 0;

		for (PaasProfile profile : profiles) {
			long numberOfMiddlewares = profile.getMiddlewares().size();
			if (numberOfMiddlewares > maxAmount) {
				maxAmount = numberOfMiddlewares;
			}
			if (middlewares.containsKey("midAmount|" + numberOfMiddlewares)) {
				middlewares.replace("midAmount|" + numberOfMiddlewares,
						middlewares.get("midAmount|" + numberOfMiddlewares) + 1);
			} else {
				middlewares.put("midAmount|" + numberOfMiddlewares, (long) 1);
			}
			for (Middleware profileMiddleware : profile.getMiddlewares()) {
				profileMiddleware.getVersions().forEach(version -> {
					String key = profileMiddleware.getName() + "|" + version;
					if (middleware.putIfAbsent(key, (long) 1) != null) {
						middleware.replace(key, middleware.get(key), middleware.get(key) + 1);
					}
				});
			}
		}
		middleware.putAll(middlewares);
		middleware.put("max", maxAmount);
	}

	private void evalFrameworks() {
		frameworks = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getFrameworks().forEach(framework -> {
				framework.getVersions().forEach(version -> {
					String key = framework.getName() + "|" + framework.getRuntime() + "|" + version;
					if (frameworks.putIfAbsent(key, (long) 1) != null) {
						frameworks.replace(key, frameworks.get(key), frameworks.get(key) + 1);
					}
				});
			});
		});
	}

	private void evalServices() {
		services = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getServices().getNative().forEach(nativeService -> {
				nativeService.getVersions().forEach(version -> {
					String key = nativeService.getName() + "|" + nativeService.getType() + "|" + version;
					if (services.putIfAbsent(key, (long) 1) != null) {
						services.replace(key, services.get(key), services.get(key) + 1);
					}
				});
			});
		});
	}

	private void evalExtensible() {
		extensible = new HashMap<String, Long>();
		extensible.put("true", (long) 0);
		extensible.put("false", (long) 0);
		extensible.put("unknown", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getExtensible().equals("true")) {
				extensible.replace("true", extensible.get("true"), extensible.get("true") + 1);
			}
			if (profile.getExtensible().equals("false")) {
				extensible.replace("false", extensible.get("false"), extensible.get("false") + 1);
			}
			if (profile.getExtensible().equals("unknown")) {
				extensible.replace("unknown", extensible.get("unknown"), extensible.get("unknown") + 1);
			}
		});
	}

	private void evalInfrastructures() {
		infrastructures = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getInfrastructures().forEach(infrastructure -> {
				String key = infrastructure.getContinent() + "|" + infrastructure.getCountry() + "|"
						+ infrastructure.getRegion() + "|" + infrastructure.getProvider();
				if (infrastructures.putIfAbsent(key, (long) 1) != null) {
					infrastructures.replace(key, infrastructures.get(key), infrastructures.get(key) + 1);
				}
			});
		});
	}

	private void evalQos() {
		qos = new HashMap<String, Double>();

		for (PaasProfile profile : profiles) {
			// Count Qos in profiles
			double uptime = profile.getQos().getUptime();
			if (!Double.isNaN(uptime)) {
				qos.put(profile.getName(), uptime);
			}
		}
	}

	private void evalCompliance() {
		compliance = new HashMap<String, Long>();

		compliance.put("size", (long) profiles.size());

		for (PaasProfile profile : profiles) {

			// "#c-" Count number of compliances in this profile
			compliance.put("#c-" + profile.getName(), (long) 0);
			if (!profile.getQos().getCompliance().isEmpty()) {
				for (String profileCompliance : profile.getQos().getCompliance()) {
					compliance.replace("#c-" + profile.getName(), compliance.get("#c-" + profile.getName()),
							compliance.get("#c-" + profile.getName()) + 1);

					if (compliance.putIfAbsent("comp|" + profileCompliance, (long) 1) != null) {
						// Count appereances of this compliance in all profiles
						compliance.replace("comp|" + profileCompliance, compliance.get("comp|" + profileCompliance),
								compliance.get("comp|" + profileCompliance) + 1);
					}
				}
			}
		}
	}
}
