package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum PricingPeriod {
	@SerializedName("daily")
	@Expose
	daily, 
	
	@SerializedName("monthly")
	@Expose
	monthly, 
	
	@SerializedName("anually")
	@Expose
	anually
}
