package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum PricingModel {
	@SerializedName("free")
	@Expose
	free, 
	
	@SerializedName("fixed")
	@Expose
	fixed, 
	
	@SerializedName("metered")
	@Expose
	metered, 
	
	@SerializedName("hybrid")
	@Expose
	hybrid,
	
	empty
}
