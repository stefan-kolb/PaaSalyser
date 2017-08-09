package statistics;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import profile.PaasProfile;
import profile.models.PricingModel;
import profile.models.PricingPeriod;
import profile.models.Runtime;
import statistics.models.ExtensibleData;
import statistics.models.HostingData;
import statistics.models.InfrastructureData;
import statistics.models.PlatformData;
import statistics.models.PricingData;
import statistics.models.RevisionData;
import statistics.models.RuntimeData;
import statistics.models.ScalingData;
import statistics.models.ServicesData;
import statistics.models.StatusData;
import statistics.models.TypeData;

public class DataPreprocessing {

	private List<PaasProfile> profiles = new ArrayList<PaasProfile>();

	private RevisionData revisionData;
	private StatusData statusData = new StatusData();;
	private TypeData typeData;
	private PlatformData platformData;
	private HostingData hostingData;
	private PricingData pricingData;
	private ScalingData scalingData;
	private RuntimeData runtimesData;
	// private Map<String, Long> middlewareData;
	// private Map<String, Long> frameworksData;
	private ServicesData servicesData;
	private ExtensibleData extensibleData;
	private InfrastructureData infrastructuresData;

	public DataPreprocessing(List<PaasProfile> profiles) {
		// Make sure that no eol-profile is being added to active profiles
		profiles.forEach(profile -> {
			if (profile.getStatus().equalsIgnoreCase("eol")) {
				statusData.incrementEol();
			} else {
				this.profiles.add(profile);
			}
		});

		// Execute all evaluations
		System.out.println("evalRevision");
		evalRevision();
		System.out.println("evalStatus");
		evalStatus();
		System.out.println("evalType");
		evalType();
		System.out.println("evalPlatform");
		evalPlatform();
		System.out.println("evalHosting");
		evalHosting();
		System.out.println("evalPricing");
		evalPricing();
		System.out.println("evalScaling");
		evalScaling();
		System.out.println("evalRuntimes");
		evalRuntimes();
		// System.out.println("evalMiddleware");
		// evalMiddleware();
		// System.out.println("evalFrameworks");
		// evalFrameworks();
		System.out.println("evalServices");
		evalServices();
		System.out.println("evalExtensible");
		evalExtensible();
		System.out.println("evalInfrastructures");
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

	public PricingData getPricingData() {
		return pricingData;
	}

	public ScalingData getScalingData() {
		return scalingData;
	}

	public RuntimeData getRuntimesData() {
		return runtimesData;
	}

	// public Map<String, Long> getMiddlewareData() {
	// return middlewareData;
	// }
	//
	// public Map<String, Long> getFrameworksData() {
	// return frameworksData;
	// }

	public ServicesData getServicesData() {
		return servicesData;
	}

	public ExtensibleData getExtensibleData() {
		return extensibleData;
	}

	public InfrastructureData getInfrastructuresData() {
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
		profiles.forEach(profile -> {
			// Evaluate Status
			if (profile.getStatus().equalsIgnoreCase("production")) {
				statusData.incrementProduction();
			} else if (profile.getStatus().equalsIgnoreCase("alpha")) {
				statusData.incrementAlpha();
			} else if (profile.getStatus().equalsIgnoreCase("beta")) {
				statusData.incrementBeta();
			}

			if (profile.getStatusSince() != null && !profile.getStatusSince().equals("null")) {
				// Evaluate StatusSince
				try {
					// The first 10 chars of the revision is always only the
					// date
					statusData.addStatusSince(profile.getName(), ChronoUnit.DAYS
							.between(LocalDate.parse(profile.getStatusSince().substring(0, 10)), LocalDate.now()));
				} catch (DateTimeParseException e) {
					e.printStackTrace();
				}
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
				typeData.incrementIaaSCentric();
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
		pricingData = new PricingData();

		profiles.forEach(profile -> {
			if (profile.getPricings().size() == 0) {
				pricingData.incrementZeroModels();
			} else if (profile.getPricings().size() == 1) {
				pricingData.incrementOneModel();
			} else if (profile.getPricings().size() == 2) {
				pricingData.incrementTwoModels();
			} else if (profile.getPricings().size() == 3) {
				pricingData.incrementThreeModels();
			} else if (profile.getPricings().size() == 4) {
				pricingData.incrementFourModels();
			}
			profile.getPricings().forEach(profilePricing -> {
				if (profilePricing.getModel().equals(PricingModel.free)) {
					pricingData.incrementFreeModels();
				} else if (profilePricing.getModel().equals(PricingModel.fixed)) {
					pricingData.incrementFixedModels();
				} else if (profilePricing.getModel().equals(PricingModel.metered)) {
					pricingData.incrementMeteredModels();
				} else if (profilePricing.getModel().equals(PricingModel.hybrid)) {
					pricingData.incrementHybridModels();
				} else if (profilePricing.getModel().equals(PricingModel.empty)) {
					pricingData.incrementEmptyModels();
				}

				if (profilePricing.getPeriod().equals(PricingPeriod.daily)) {
					pricingData.incrementDailyPeriod();
				} else if (profilePricing.getPeriod().equals(PricingPeriod.monthly)) {
					pricingData.incrementMonthlyPeriod();
				} else if (profilePricing.getPeriod().equals(PricingPeriod.anually)) {
					pricingData.incrementAnuallyPeriod();
				} else if (profilePricing.getPeriod().equals(PricingPeriod.empty)) {
					pricingData.incrementEmptyPeriod();
				}
			});
		});
	}

	private void evalScaling() {
		scalingData = new ScalingData();

		profiles.forEach(profile -> {
			if (profile.getScaling().getVertical()) {
				scalingData.incrementVertical();
			}
			if (profile.getScaling().getHorizontal()) {
				scalingData.incrementHorizontal();
			}
			if (profile.getScaling().getAuto()) {
				scalingData.incrementAuto();
			}
		});
	}

	private void evalRuntimes() {
		runtimesData = new RuntimeData();
		for (PaasProfile profile : profiles) {
			runtimesData.addProfileRuntimeAmount(profile.getName(), profile.getRuntimes().size());
			for (Runtime runtime : profile.getRuntimes()) {
				runtimesData.addRuntime(runtime.getLanguage());
			}
		}
	}

	// private void evalMiddleware() {
	// middlewareData = new HashMap<String, Long>();
	//
	// Map<String, Long> middlewares = new HashMap<String, Long>();
	//
	// long maxAmount = 0;
	//
	// for (PaasProfile profile : profiles) {
	// long numberOfMiddlewares = profile.getMiddlewares().size();
	// if (numberOfMiddlewares > maxAmount) {
	// maxAmount = numberOfMiddlewares;
	// }
	// if (middlewares.containsKey("midAmount|" + numberOfMiddlewares)) {
	// middlewares.replace("midAmount|" + numberOfMiddlewares,
	// middlewares.get("midAmount|" + numberOfMiddlewares) + 1);
	// } else {
	// middlewares.put("midAmount|" + numberOfMiddlewares, (long) 1);
	// }
	// for (Middleware profileMiddleware : profile.getMiddlewares()) {
	// profileMiddleware.getVersions().forEach(version -> {
	// String key = profileMiddleware.getName() + "|" + version;
	// if (middlewareData.putIfAbsent(key, (long) 1) != null) {
	// middlewareData.replace(key, middlewareData.get(key),
	// middlewareData.get(key) + 1);
	// }
	// });
	// }
	// }
	// middlewareData.putAll(middlewares);
	// middlewareData.put("max", maxAmount);
	// }
	//
	// private void evalFrameworks() {
	// frameworksData = new HashMap<String, Long>();
	// profiles.forEach(profile -> {
	// profile.getFrameworks().forEach(framework -> {
	// framework.getVersions().forEach(version -> {
	// String key = framework.getName() + "|" + framework.getRuntime() + "|" +
	// version;
	// if (frameworksData.putIfAbsent(key, (long) 1) != null) {
	// frameworksData.replace(key, frameworksData.get(key),
	// frameworksData.get(key) + 1);
	// }
	// });
	// });
	// });
	// }

	private void evalServices() {
		servicesData = new ServicesData();
		profiles.forEach(profile -> {
			servicesData.addProfilesWithNativeServices(profile.getName(), profile.getServices().getNative().size());
			profile.getServices().getNative().forEach(nativeService -> {
				servicesData.addNativeService(nativeService.getName(), nativeService.getType());
			});
		});
	}

	private void evalExtensible() {
		extensibleData = new ExtensibleData();

		profiles.forEach(profile -> {
			if (profile.getExtensible().equalsIgnoreCase("true")) {
				extensibleData.incrementYes();
			}
		});
	}

	private void evalInfrastructures() {
		infrastructuresData = new InfrastructureData();
		profiles.forEach(profile -> {
			infrastructuresData.addInfraStructureProfile(profile.getName(), profile.getInfrastructures());

			profile.getInfrastructures().forEach(infrastructure -> {
				infrastructuresData.addContinent(infrastructure.getContinent());
				infrastructuresData.addCountry(infrastructure.getCountry());
				infrastructuresData.addRegion(infrastructure.getRegion());
				infrastructuresData.addProvider(infrastructure.getProvider());
			});
		});
	}
}
