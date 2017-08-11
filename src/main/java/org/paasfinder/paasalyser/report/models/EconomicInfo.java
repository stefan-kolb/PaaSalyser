package org.paasfinder.paasalyser.report.models;

public class EconomicInfo {

	private HostingReport hostingReport;
	private ScalingReport scalingReport;
	private RuntimesReport runtimesReport;
	private MiddlewareReport middlewareReport;
	private FrameworksReport frameworksReport;
	private ServicesReport servicesReport;
	private ExtensibleReport extensibleReport;
	private InfrastructuresReport infrastructuresReport;

	public EconomicInfo(HostingReport hostingReport, ScalingReport scalingReport, RuntimesReport runtimesReport,
			MiddlewareReport middlewareReport, FrameworksReport frameworksReport, ServicesReport servicesReport,
			ExtensibleReport extensibleReport, InfrastructuresReport infrastructuresReport) {
		super();
		this.hostingReport = hostingReport;
		this.scalingReport = scalingReport;
		this.runtimesReport = runtimesReport;
		this.middlewareReport = middlewareReport;
		this.frameworksReport = frameworksReport;
		this.servicesReport = servicesReport;
		this.extensibleReport = extensibleReport;
		this.infrastructuresReport = infrastructuresReport;
	}

	public HostingReport getHostingReport() {
		return hostingReport;
	}

	public ScalingReport getScalingReport() {
		return scalingReport;
	}

	public RuntimesReport getRuntimesReport() {
		return runtimesReport;
	}

	public MiddlewareReport getMiddlewareReport() {
		return middlewareReport;
	}

	public FrameworksReport getFrameworksReport() {
		return frameworksReport;
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
