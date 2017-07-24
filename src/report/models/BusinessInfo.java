package report.models;

public class BusinessInfo {

	private StatusReport statusReport;
	private PricingReport pricingReport;

	public BusinessInfo(StatusReport statusReport, PricingReport pricingReport) {
		super();
		this.statusReport = statusReport;
		this.pricingReport = pricingReport;
	}

	public StatusReport getStatusReport() {
		return statusReport;
	}

	public PricingReport getPricingReport() {
		return pricingReport;
	}

}
