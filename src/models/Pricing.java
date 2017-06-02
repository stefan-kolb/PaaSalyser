package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pricing {

	@SerializedName("model")
	@Expose
	private String model;

	@SerializedName("period")
	@Expose
	private String period;

	public String getPeriod() {
		return period;
	}

	public String getModel() {
		return model;
	}

}