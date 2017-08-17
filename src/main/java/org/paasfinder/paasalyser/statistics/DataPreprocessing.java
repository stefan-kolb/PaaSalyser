package org.paasfinder.paasalyser.statistics;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.paasfinder.paasalyser.profile.PaasProfile;
import org.paasfinder.paasalyser.profile.models.Infrastructure;
import org.paasfinder.paasalyser.profile.models.NativeService;
import org.paasfinder.paasalyser.profile.models.Pricing;
import org.paasfinder.paasalyser.profile.models.PricingModel;
import org.paasfinder.paasalyser.profile.models.PricingPeriod;
import org.paasfinder.paasalyser.profile.models.Runtime;
import org.paasfinder.paasalyser.statistics.models.ExtensibleData;
import org.paasfinder.paasalyser.statistics.models.HostingData;
import org.paasfinder.paasalyser.statistics.models.InfrastructureData;
import org.paasfinder.paasalyser.statistics.models.PlatformData;
import org.paasfinder.paasalyser.statistics.models.PricingData;
import org.paasfinder.paasalyser.statistics.models.RevisionData;
import org.paasfinder.paasalyser.statistics.models.RuntimeData;
import org.paasfinder.paasalyser.statistics.models.ScalingData;
import org.paasfinder.paasalyser.statistics.models.ServicesData;
import org.paasfinder.paasalyser.statistics.models.StatusData;
import org.paasfinder.paasalyser.statistics.models.TypeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataPreprocessing {

	private final Logger logger = LoggerFactory.getLogger(DataPreprocessing.class);

	private List<PaasProfile> profiles = new ArrayList<PaasProfile>();

	private int invalidProfilesCount;
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
		sortOutBadProfiles(profiles);

		// Execute all evaluations
		logger.debug("evalRevision");
		evalRevision();
		logger.debug("evalStatus");
		evalStatus();
		logger.debug("evalType");
		evalType();
		logger.debug("evalPlatform");
		evalPlatform();
		logger.debug("evalHosting");
		evalHosting();
		logger.debug("evalPricing");
		evalPricing();
		logger.debug("evalScaling");
		evalScaling();
		logger.debug("evalRuntimes");
		evalRuntimes();
		// logger.debug("evalMiddleware");
		// evalMiddleware();
		// logger.debug("evalFrameworks");
		// evalFrameworks();
		logger.debug("evalServices");
		evalServices();
		logger.debug("evalExtensible");
		evalExtensible();
		logger.debug("evalInfrastructures");
		evalInfrastructures();
	}

	public List<PaasProfile> getProfiles() {
		return profiles;
	}

	public int getInvalidProfilesCount() {
		return invalidProfilesCount;
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

	private void sortOutBadProfiles(List<PaasProfile> profiles) {
		// Make sure that no eol-profile is being added to active profiles
		for (PaasProfile profile : profiles) {
			try {
				if (profile.getStatus().equalsIgnoreCase("eol")) {
					statusData.incrementEol();
				} else {
					this.profiles.add(profile);
				}
			} catch (NullPointerException e) {
				logger.debug("Bad profile found");
				invalidProfilesCount++;
			}
		}
	}

	private void evalRevision() {
		revisionData = new RevisionData();
		for (PaasProfile profile : profiles) {
			try {
				// The first 10 chars of the revision is always only the date
				revisionData.addRevision(profile.getName(), ChronoUnit.DAYS
						.between(LocalDate.parse(profile.getRevision().substring(0, 10)), LocalDate.now()));
			} catch (DateTimeParseException e) {
				logger.debug("StatusSince could not be parsed: " + profile.getRevision());
			}
		}
	}

	private void evalStatus() {
		statusData = new StatusData();
		for (PaasProfile profile : profiles) {
			// Evaluate Status
			if (profile.getStatus().equalsIgnoreCase("production")) {
				statusData.incrementProduction();
			} else if (profile.getStatus().equalsIgnoreCase("alpha")) {
				statusData.incrementAlpha();
			} else if (profile.getStatus().equalsIgnoreCase("beta")) {
				statusData.incrementBeta();
			}

			// Evaluate StatusSince
			if (profile.getStatusSince() == null) {
				logger.debug("Status Since was null in: " + profile.getName());
				continue;
			}
			try {
				// The first 10 chars of the revision is always only the
				// date
				statusData.addStatusSince(profile.getName(), ChronoUnit.DAYS
						.between(LocalDate.parse(profile.getStatusSince().substring(0, 10)), LocalDate.now()));
			} catch (DateTimeParseException e) {
				logger.debug("StatusSince could not be parsed: " + profile.getStatusSince());
			}

		}
	}

	private void evalType() {
		typeData = new TypeData();
		for (PaasProfile profile : profiles) {
			if (profile.getType().equalsIgnoreCase("SaaS-centric")) {
				typeData.incrementSaasCentric();
			} else if (profile.getType().equalsIgnoreCase("Generic")) {
				typeData.incrementGeneric();
			} else if (profile.getType().equalsIgnoreCase("IaaS-centric")) {
				typeData.incrementIaaSCentric();
			}
		}
	}

	private void evalPlatform() {
		platformData = new PlatformData();
		for (PaasProfile profile : profiles) {
			if (profile.getPlatform() == null) {
				continue;
			}
			if (profile.getPlatform().equals("null")) {
				continue;
			}
			if (profile.getPlatform().isEmpty()) {
				continue;
			}
			platformData.addPlatform(profile.getPlatform());
		}
	}

	public void evalHosting() {
		hostingData = new HostingData();
		for (PaasProfile profile : profiles) {
			if (profile.getHosting().getPublic()) {
				hostingData.incrementPublic();
			}
			if (profile.getHosting().getPrivate()) {
				hostingData.incrementPrivate();
			}
			if (profile.getHosting().getVirtualPrivate()) {
				// TODO check if this is ok or is sometimes null.
				hostingData.incrementVirtualPrivate();
			}
		}
	}

	private void evalPricing() {
		pricingData = new PricingData();
		for (PaasProfile profile : profiles) {
			if (profile.getPricings() == null) {
				continue;
			}

			if (profile.getPricings().length == 0) {
				pricingData.incrementZeroModels();

				// Skip rest as array is empty!
				continue;
			} else if (profile.getPricings().length == 1) {
				pricingData.incrementOneModel();
			} else if (profile.getPricings().length == 2) {
				pricingData.incrementTwoModels();
			} else if (profile.getPricings().length == 3) {
				pricingData.incrementThreeModels();
			} else if (profile.getPricings().length == 4) {
				pricingData.incrementFourModels();
			}
			for (Pricing profilePricing : profile.getPricings()) {
				if (profilePricing.getModel().equals(PricingModel.free)) {
					pricingData.incrementFreeModels();
				} else if (profilePricing.getModel().equals(PricingModel.fixed)) {
					pricingData.incrementFixedModels();
				} else if (profilePricing.getModel().equals(PricingModel.metered)) {
					pricingData.incrementMeteredModels();
				} else if (profilePricing.getModel().equals(PricingModel.hybrid)) {
					pricingData.incrementHybridModels();
				}

				if (profilePricing.getPeriod() == null) {
					continue;
				}
				// If PricingModel is free, no PricingPeriod is needed!
				if (profilePricing.getModel().equals(PricingModel.free)) {
					continue;
				}
				if (profilePricing.getPeriod().equals(PricingPeriod.daily)) {
					pricingData.incrementDailyPeriod();
				} else if (profilePricing.getPeriod().equals(PricingPeriod.monthly)) {
					pricingData.incrementMonthlyPeriod();
				} else if (profilePricing.getPeriod().equals(PricingPeriod.anually)) {
					pricingData.incrementAnuallyPeriod();
				}
			}
		}
	}

	private void evalScaling() {
		scalingData = new ScalingData();

		for (PaasProfile profile : profiles) {
			if (profile.getScaling() == null) {
				continue;
			}

			if (profile.getScaling().getVertical()) {
				scalingData.incrementVertical();
			}
			if (profile.getScaling().getHorizontal()) {
				scalingData.incrementHorizontal();
			}
			if (profile.getScaling().getAuto()) {
				scalingData.incrementAuto();
			}
		}
	}

	private void evalRuntimes() {
		runtimesData = new RuntimeData();
		for (PaasProfile profile : profiles) {
			if (profile.getRuntimes() == null) {
				continue;
			}

			runtimesData.addProfileRuntimeAmount(profile.getName(), profile.getRuntimes().length);
			for (Runtime runtime : profile.getRuntimes()) {
				runtimesData.addRuntime(runtime.getLanguage());
			}
		}
	}

	private void evalServices() {
		servicesData = new ServicesData();
		for (PaasProfile profile : profiles) {
			if (profile.getServices() == null) {
				continue;
			}

			servicesData.addProfilesWithNativeServices(profile.getName(), profile.getServices().getNative().length);
			for (NativeService nativeService : profile.getServices().getNative()) {
				servicesData.addNativeService(nativeService.getName(), nativeService.getType());
			}
		}
	}

	private void evalExtensible() {
		extensibleData = new ExtensibleData();
		for (PaasProfile profile : profiles) {
			// boolean can only be false or true, not null!
			if (profile.isExtensible() == true) {
				extensibleData.incrementTrue();
			}
		}
	}

	private void evalInfrastructures() {
		infrastructuresData = new InfrastructureData();
		for (PaasProfile profile : profiles) {
			if (profile.getInfrastructures() == null) {
				continue;
			}

			infrastructuresData.addInfraStructureProfile(profile.getName(), profile.getInfrastructures());

			for (Infrastructure infrastructure : profile.getInfrastructures()) {
				infrastructuresData.addContinent(infrastructure.getContinent());
				infrastructuresData.addCountry(infrastructure.getCountry());
				infrastructuresData.addRegion(infrastructure.getRegion());
				infrastructuresData.addProvider(infrastructure.getProvider());
			}
		}
	}
}
