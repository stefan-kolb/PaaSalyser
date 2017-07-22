package report.models;

public class BusinessInfo {

	private StatusReport statusReport;
	private StatusSinceReport statusSinceReport;
	private QosReport qosReport;
	private OverallComplianceReport overallComplianceReport;
	private SpecificComplianceReport specificComplianceReport;
	private PricingReport pricingReport;
	
	public BusinessInfo(StatusReport statusReport, StatusSinceReport statusSinceReport, QosReport qosReport,
			OverallComplianceReport overallComplianceReport, SpecificComplianceReport specificComplianceReport,
			PricingReport pricingReport) {
		super();
		this.statusReport = statusReport;
		this.statusSinceReport = statusSinceReport;
		this.qosReport = qosReport;
		this.overallComplianceReport = overallComplianceReport;
		this.specificComplianceReport = specificComplianceReport;
		this.pricingReport = pricingReport;
	}
	public StatusReport getStatusReport() {
		return statusReport;
	}
	public StatusSinceReport getStatusSinceReport() {
		return statusSinceReport;
	}
	public QosReport getQosReport() {
		return qosReport;
	}
	public OverallComplianceReport getOverallComplianceReport() {
		return overallComplianceReport;
	}
	public SpecificComplianceReport getSpecificComplianceReport() {
		return specificComplianceReport;
	}
	public PricingReport getPricingReport() {
		return pricingReport;
	}
}
