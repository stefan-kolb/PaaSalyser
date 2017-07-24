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
import statistics.models.HostingData;
import statistics.models.PlatformData;
import statistics.models.RevisionData;
import statistics.models.StatusData;
import statistics.models.TypeData;

public class DataPreprocessing {

	private List<PaasProfile> profiles = new ArrayList<PaasProfile>();

	private RevisionData revisionData;
	private StatusData statusData;
	private TypeData typeData;
	private PlatformData platformData;
	private HostingData hostingData;
	private Map<String, Long> pricingData;
	private Map<String, Long> scalingData;
	private Map<String, Long> runtimesData;
	private Map<String, Long> middlewareData;
	private Map<String, Long> frameworksData;
	private Map<String, Long> servicesData;
	private Map<String, Long> extensibleData;
	private Map<String, Long> infrastructuresData;

	public DataPreprocessing(List<PaasProfile> profiles) {
		// Make sure that no eol-profile is being added to active profiles
		profiles.forEach(profile -> {
			if (profile.getStatus().equalsIgnoreCase("eol")) {
				statusData.incrementEol();
			} else {
				profiles.add(profile);
			}
		});

		// Execute all evaluations
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

	public List<PaasProfile> getProfiles() {
		return profiles;
	}

	public RevisionData getRevisionData() {
		return revisionData;
	}

	public StatusData getStatusData() {
		return statusData;
	}

	public TypeData getTypeData() {
		return typeData;
	}

	public PlatformData getPlatformData() {
		return platformData;
	}

	public HostingData getHostingData() {
		return hostingData;
	}

	public Map<String, Long> getPricingData() {
		return pricingData;
	}

	public Map<String, Long> getScalingData() {
		return scalingData;
	}

	public Map<String, Long> getRuntimesData() {
		return runtimesData;
	}

	public Map<String, Long> getMiddlewareData() {
		return middlewareData;
	}

	public Map<String, Long> getFrameworksData() {
		return frameworksData;
	}

	public Map<String, Long> getServicesData() {
		return servicesData;
	}

	public Map<String, Long> getExtensibleData() {
		return extensibleData;
	}

	public Map<String, Long> getInfrastructuresData() {
		return infrastructuresData;
	}

	private void evalRevision() {
		revisionData = new RevisionData();
		for (PaasProfile profile : profiles) {
			try {
				// The first 10 chars of the revision is always only the date
				revisionData.addRevision(profile.getName(), ChronoUnit.DAYS
						.between(LocalDate.parse(profile.getRevision().substring(0, 10)), LocalDate.now()));
			} catch (DateTimeParseException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void evalStatus() {
		statusData = new StatusData();
		profiles.forEach(profile -> {
			// Evaluate Status
			if (profile.getStatus().equalsIgnoreCase("production")) {
				statusData.incrementProduction();
			} else if (profile.getStatus().equalsIgnoreCase("alpha")) {
				statusData.incrementAlpha();
			} else if (profile.getStatus().equalsIgnoreCase("beta")) {
				statusData.incrementBeta();
			}

			// Evaluate StatusSince
			try {
				// The first 10 chars of the revision is always only the
				// date
				statusData.addStatusSince(profile.getName(), ChronoUnit.DAYS
						.between(LocalDate.parse(profile.getStatusSince().substring(0, 10)), LocalDate.now()));
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		});
	}

	private void evalType() {
		typeData = new TypeData();

		profiles.forEach(profile -> {
			if (profile.getType().equalsIgnoreCase("SaaS-centric")) {
				typeData.incrementSaasCentric();
			} else if (profile.getType().equalsIgnoreCase("Generic")) {
				typeData.incrementGeneric();
			} else if (profile.getType().equalsIgnoreCase("IaaS-centric")) {
				typeData.incrementSaasCentric();
			}
		});
	}

	private void evalPlatform() {
		platformData = new PlatformData();

		profiles.forEach(profile -> {
			if (!(profile.getPlatform() == null || profile.getPlatform().equals("null"))) {
				platformData.addPlatform(profile.getPlatform());
			}
		});
	}

	public void evalHosting() {
		hostingData = new HostingData();
		
		profiles.forEach(profile -> {
			if (profile.getHosting().getPublic()) {
				hostingData.incrementPublic();
			}
			if (profile.getHosting().getPrivate()) {
				hostingData.incrementPrivate();
			}
			if (profile.getHosting().getVirtualPrivate()) {
				hostingData.incrementVirtualPrivate();
			}
		});
	}

	private void evalPricing() {
		pricingData = new HashMap<String, Long>();
		pricingData.put("modelcounter0", (long) 0);
		pricingData.put("modelcounter1", (long) 0);
		pricingData.put("modelcounter2", (long) 0);
		pricingData.put("modelcounter3", (long) 0);
		pricingData.put("modelcounter4", (long) 0);
		pricingData.put("modelfree", (long) 0);
		pricingData.put("modelfixed", (long) 0);
		pricingData.put("modelmetered", (long) 0);
		pricingData.put("modelhybrid", (long) 0);
		pricingData.put("modelempty", (long) 0);

		pricingData.put("perioddaily", (long) 0);
		pricingData.put("periodmonthly", (long) 0);
		pricingData.put("periodanually", (long) 0);
		pricingData.put("periodempty", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getPricings().size() == 0) {
				pricingData.replace("modelcounter0", pricingData.get("modelcounter0"),
						pricingData.get("modelcounter0") + 1);
			} else if (profile.getPricings().size() == 1) {
				pricingData.replace("modelcounter1", pricingData.get("modelcounter1"),
						pricingData.get("modelcounter1") + 1);
			} else if (profile.getPricings().size() == 2) {
				pricingData.replace("modelcounter2", pricingData.get("modelcounter2"),
						pricingData.get("modelcounter2") + 1);
			} else if (profile.getPricings().size() == 3) {
				pricingData.replace("modelcounter3", pricingData.get("modelcounter3"),
						pricingData.get("modelcounter3") + 1);
			} else if (profile.getPricings().size() == 4) {
				pricingData.replace("modelcounter4", pricingData.get("modelcounter4"),
						pricingData.get("modelcounter4") + 1);
			}
			profile.getPricings().forEach(profilePricing -> {
				if (profilePricing.getModel().equals(PricingModel.free)) {
					pricingData.replace("modelfree", pricingData.get("modelfree"), pricingData.get("modelfree") + 1);
				} else if (profilePricing.getModel().equals(PricingModel.fixed)) {
					pricingData.replace("modelfixed", pricingData.get("modelfixed"), pricingData.get("modelfixed") + 1);
				} else if (profilePricing.getModel().equals(PricingModel.metered)) {
					pricingData.replace("modelmetered", pricingData.get("modelmetered"),
							pricingData.get("modelmetered") + 1);
				} else if (profilePricing.getModel().equals(PricingModel.hybrid)) {
					pricingData.replace("modelhybrid", pricingData.get("modelhybrid"),
							pricingData.get("modelhybrid") + 1);
				} else if (profilePricing.getModel().equals(PricingModel.empty)) {
					pricingData.replace("modelempty", pricingData.get("modelempty"), pricingData.get("modelempty") + 1);
				}

				if (profilePricing.getPeriod().equals(PricingPeriod.daily)) {
					pricingData.replace("perioddaily", pricingData.get("perioddaily"),
							pricingData.get("perioddaily") + 1);
				} else if (profilePricing.getPeriod().equals(PricingPeriod.monthly)) {
					pricingData.replace("periodmonthly", pricingData.get("periodmonthly"),
							pricingData.get("periodmonthly") + 1);
				} else if (profilePricing.getPeriod().equals(PricingPeriod.anually)) {
					pricingData.replace("periodanually", pricingData.get("periodanually"),
							pricingData.get("periodanually") + 1);
				} else if (profilePricing.getPeriod().equals(PricingPeriod.empty)) {
					pricingData.replace("periodempty", pricingData.get("periodempty"),
							pricingData.get("periodempty") + 1);
				}
			});
		});
	}

	private void evalScaling() {
		scalingData = new HashMap<String, Long>();
		scalingData.put("vertical", (long) 0);
		scalingData.put("horizontal", (long) 0);
		scalingData.put("auto", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getScaling().getVertical()) {
				scalingData.replace("vertical", scalingData.get("vertical"), scalingData.get("vertical") + 1);
			}
			if (profile.getScaling().getHorizontal()) {
				scalingData.replace("horizontal", scalingData.get("horizontal"), scalingData.get("horizontal") + 1);
			}
			if (profile.getScaling().getAuto()) {
				scalingData.replace("auto", scalingData.get("auto"), scalingData.get("auto") + 1);
			}
		});
	}

	private void evalRuntimes() {
		runtimesData = new HashMap<String, Long>();
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
					if (runtimesData.putIfAbsent(key, (long) 1) != null) {
						runtimesData.replace(key, runtimesData.get(key), runtimesData.get(key) + 1);
					}
				}
			}
		}

		runtimesData.putAll(runtimeAmounts);
		runtimesData.putAll(versionAmounts);
		runtimesData.put("maxRuntimes", maxAmountRuntimes);
		runtimesData.put("maxVersions", maxAmountVersions);
	}

	private void evalMiddleware() {
		middlewareData = new HashMap<String, Long>();

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
					if (middlewareData.putIfAbsent(key, (long) 1) != null) {
						middlewareData.replace(key, middlewareData.get(key), middlewareData.get(key) + 1);
					}
				});
			}
		}
		middlewareData.putAll(middlewares);
		middlewareData.put("max", maxAmount);
	}

	private void evalFrameworks() {
		frameworksData = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getFrameworks().forEach(framework -> {
				framework.getVersions().forEach(version -> {
					String key = framework.getName() + "|" + framework.getRuntime() + "|" + version;
					if (frameworksData.putIfAbsent(key, (long) 1) != null) {
						frameworksData.replace(key, frameworksData.get(key), frameworksData.get(key) + 1);
					}
				});
			});
		});
	}

	private void evalServices() {
		servicesData = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getServices().getNative().forEach(nativeService -> {
				nativeService.getVersions().forEach(version -> {
					String key = nativeService.getName() + "|" + nativeService.getType() + "|" + version;
					if (servicesData.putIfAbsent(key, (long) 1) != null) {
						servicesData.replace(key, servicesData.get(key), servicesData.get(key) + 1);
					}
				});
			});
		});
	}

	private void evalExtensible() {
		extensibleData = new HashMap<String, Long>();
		extensibleData.put("true", (long) 0);
		extensibleData.put("false", (long) 0);
		extensibleData.put("unknown", (long) 0);

		profiles.forEach(profile -> {
			if (profile.getExtensible().equals("true")) {
				extensibleData.replace("true", extensibleData.get("true"), extensibleData.get("true") + 1);
			}
			if (profile.getExtensible().equals("false")) {
				extensibleData.replace("false", extensibleData.get("false"), extensibleData.get("false") + 1);
			}
			if (profile.getExtensible().equals("unknown")) {
				extensibleData.replace("unknown", extensibleData.get("unknown"), extensibleData.get("unknown") + 1);
			}
		});
	}

	private void evalInfrastructures() {
		infrastructuresData = new HashMap<String, Long>();
		profiles.forEach(profile -> {
			profile.getInfrastructures().forEach(infrastructure -> {
				String key = infrastructure.getContinent() + "|" + infrastructure.getCountry() + "|"
						+ infrastructure.getRegion() + "|" + infrastructure.getProvider();
				if (infrastructuresData.putIfAbsent(key, (long) 1) != null) {
					infrastructuresData.replace(key, infrastructuresData.get(key), infrastructuresData.get(key) + 1);
				}
			});
		});
	}
}
