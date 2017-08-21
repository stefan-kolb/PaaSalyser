package org.paasfinder.paasalyser.profile.models;

public class Pricing {

	private PricingModel model;
	private PricingPeriod period;

	public Pricing(PricingModel model, PricingPeriod period) {
		super();
		this.model = model;
		this.period = period;
	}

	public PricingPeriod getPeriod() {
		return period;
	}

	public PricingModel getModel() {
		return model;
	}

}