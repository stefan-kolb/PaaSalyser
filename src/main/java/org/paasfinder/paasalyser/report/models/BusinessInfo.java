package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class BusinessInfo {

	@Embedded
	private StatusReport statusReport;
	@Embedded
	private PricingReport pricingReport;

	public BusinessInfo(StatusReport statusReport, PricingReport pricingReport) {
		super();
		this.statusReport = statusReport;
		this.pricingReport = pricingReport;
	}

	public BusinessInfo() {
		super();
	}

	public StatusReport getStatusReport() {
		return statusReport;
	}

	public PricingReport getPricingReport() {
		return pricingReport;
	}

}
