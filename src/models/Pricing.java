package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pricing {

	@SerializedName("model")
	@Expose
	private PricingModel model;

	@SerializedName("period")
	@Expose
	private PricingPeriod period;

	public PricingPeriod getPeriod() {
		if (period != null)
			return period;
		else return PricingPeriod.empty;
	}

	public PricingModel getModel() {
		if (model != null)
			return model;
		else
			return PricingModel.empty;
	}

}