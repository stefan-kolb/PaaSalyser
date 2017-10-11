package org.paasfinder.paasalyser.statistics.report;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.paasfinder.paasalyser.profile.PaasProfile;
import org.paasfinder.paasalyser.profile.models.Infrastructure;
import org.paasfinder.paasalyser.profile.models.NativeService;
import org.paasfinder.paasalyser.profile.models.Pricing;
import org.paasfinder.paasalyser.profile.models.PricingModel;
import org.paasfinder.paasalyser.profile.models.PricingPeriod;
import org.paasfinder.paasalyser.profile.models.Runtime;
import org.paasfinder.paasalyser.statistics.report.models.ExtensibleData;
import org.paasfinder.paasalyser.statistics.report.models.HostingData;
import org.paasfinder.paasalyser.statistics.report.models.InfrastructureData;
import org.paasfinder.paasalyser.statistics.report.models.PlatformData;
import org.paasfinder.paasalyser.statistics.report.models.PricingData;
import org.paasfinder.paasalyser.statistics.report.models.RevisionData;
import org.paasfinder.paasalyser.statistics.report.models.RuntimeData;
import org.paasfinder.paasalyser.statistics.report.models.ScalingData;
import org.paasfinder.paasalyser.statistics.report.models.ServicesData;
import org.paasfinder.paasalyser.statistics.report.models.StatusData;
import org.paasfinder.paasalyser.statistics.report.models.TypeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportPreprocessing {

	private final Logger logger = LoggerFactory.getLogger(ReportPreprocessing.class);

	private List<PaasProfile> profiles = new ArrayList<>(100);

	private LocalDate localDate;

	private int invalidProfilesCount;
	private RevisionData revisionData;
	private StatusData statusData;
	private TypeData typeData;
	private PlatformData platformData;
	private HostingData hostingData;
	private PricingData pricingData;
	private ScalingData scalingData;
	private RuntimeData runtimesData;
	private ServicesData servicesData;
	private ExtensibleData extensibleData;
	private InfrastructureData infrastructuresData;

	public ReportPreprocessing(LocalDate localDate, List<PaasProfile> profiles) throws IllegalStateException {

		// Make sure that no eol-profile is being added to active profiles
		sortOutInvalidProfiles(profiles);
		if (this.profiles.isEmpty()) {
			throw new IllegalStateException("No profiles to scan.");
		}
		if (invalidProfilesCount > 0) {
			logger.warn("Invalid profiles found.");
			return;
		}

		this.localDate = localDate;

		// Execute all evaluations
		evalRevision();
		evalStatus();
		evalType();
		evalPlatform();
		evalHosting();
		evalPricing();
		evalScaling();
		evalRuntimes();
		evalServices();
		evalExtensible();
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

	public ServicesData getServicesData() {
		return servicesData;
	}

	public ExtensibleData getExtensibleData() {
		return extensibleData;
	}

	public InfrastructureData getInfrastructuresData() {
		return infrastructuresData;
	}

	private void sortOutInvalidProfiles(List<PaasProfile> profiles) {
		// Make sure that no eol-profile is being added to active profiles
		statusData = new StatusData();
		for (PaasProfile profile : profiles) {
			if (profile == null) {
				invalidProfilesCount++;
				continue;
			}
			if (profile.getStatus().equalsIgnoreCase("eol")) {
				statusData.incrementEol();
			} else {
				this.profiles.add(profile);
			}
		}
	}

	private void evalRevision() {
		revisionData = new RevisionData();
		for (PaasProfile profile : profiles) {
			try {
				// The first 10 chars of the revision is always only the date
				if (profile.getRevision().length() < 10) {
					continue;
				}
				revisionData.addRevision(profile.getName(),
						ChronoUnit.DAYS.between(LocalDate.parse(profile.getRevision().substring(0, 10)), localDate));
			} catch (DateTimeParseException e) {
				throw new RuntimeException("Could not parse revision date", e);
			}
		}
	}

	private void evalStatus() {
		for (PaasProfile profile : profiles) {
			if (profile.getStatus().equalsIgnoreCase("production")) {
				statusData.incrementProduction();
			} else if (profile.getStatus().equalsIgnoreCase("alpha")) {
				statusData.incrementAlpha();
			} else if (profile.getStatus().equalsIgnoreCase("beta")) {
				statusData.incrementBeta();
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
			if (profile.getHosting().getPublic() && profile.getHosting().getPrivate()) {
				hostingData.incrementHybrid();
			}
			if (profile.getHosting().getVirtualPrivate()) {
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
				if (profilePricing.getModel().equals(PricingModel.free)) {
					// If PricingModel is free, no PricingPeriod is needed!
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

			boolean scalable = false;

			if (profile.getScaling().getVertical()) {
				scalingData.incrementVertical();
				scalable = true;
			}
			if (profile.getScaling().getHorizontal()) {
				scalingData.incrementHorizontal();
				scalable = true;
			}
			if (profile.getScaling().getAuto()) {
				scalingData.incrementAuto();
				scalable = true;
			}

			if (scalable) {
				scalingData.incrementScalable();
			}
		}
	}

	private void evalRuntimes() {
		runtimesData = new RuntimeData();
		for (PaasProfile profile : profiles) {
			if (profile.getRuntimes() == null) {
				continue;
			}
			if (profile.getRuntimes().length == 0) {
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

			if (profile.getServices().getNative() == null) {
				continue;
			}

			servicesData.addProfileWithNativeServices(profile.getName(), profile.getServices().getNative().length);
			for (NativeService nativeService : profile.getServices().getNative()) {
				if (nativeService == null || nativeService.getName() == null || nativeService.getName().isEmpty()) {
					continue;
				}
				if (nativeService.getType() == null || nativeService.getType().isEmpty()) {
					servicesData.addNativeService(nativeService.getName(), "empty");
				} else {
					servicesData.addNativeService(nativeService.getName(), nativeService.getType());
				}
			}
		}
	}

	private void evalExtensible() {
		extensibleData = new ExtensibleData();
		for (PaasProfile profile : profiles) {
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
			if (profile.getInfrastructures().length == 0) {
				continue;
			}
			if (!(profile.getHosting().getPublic() || profile.getHosting().getVirtualPrivate())) {
				// There are no infrastructures if no public or virtual-private
				// hosting is offered
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
