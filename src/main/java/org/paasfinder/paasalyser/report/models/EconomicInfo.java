package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class EconomicInfo {

	@Embedded
	private HostingReport hostingReport;
	@Embedded
	private PlatformReport platformReport;
	@Embedded
	private ScalingReport scalingReport;
	@Embedded
	private RuntimesReport runtimesReport;
	@Embedded
	private ServicesReport servicesReport;
	@Embedded
	private ExtensibleReport extensibleReport;
	@Embedded
	private InfrastructuresReport infrastructuresReport;

	public EconomicInfo(HostingReport hostingReport, PlatformReport platformReport, ScalingReport scalingReport,
			RuntimesReport runtimesReport, ServicesReport servicesReport, ExtensibleReport extensibleReport,
			InfrastructuresReport infrastructuresReport) {
		super();
		this.hostingReport = hostingReport;
		this.platformReport = platformReport;
		this.scalingReport = scalingReport;
		this.runtimesReport = runtimesReport;
		this.servicesReport = servicesReport;
		this.extensibleReport = extensibleReport;
		this.infrastructuresReport = infrastructuresReport;
	}

	public EconomicInfo() {
		super();
	}

	public HostingReport getHostingReport() {
		return hostingReport;
	}

	public PlatformReport getPlatformReport() {
		return platformReport;
	}

	public ScalingReport getScalingReport() {
		return scalingReport;
	}

	public RuntimesReport getRuntimesReport() {
		return runtimesReport;
	}

	public ServicesReport getServicesReport() {
		return servicesReport;
	}

	public ExtensibleReport getExtensibleReport() {
		return extensibleReport;
	}

	public InfrastructuresReport getInfrastructuresReport() {
		return infrastructuresReport;
	}

}
