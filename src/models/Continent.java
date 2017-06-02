package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum Continent {
	@SerializedName("AF")
	@Expose
	AF, 
	
	@SerializedName("AS")
	@Expose
	AS, 
	
	@SerializedName("EU")
	@Expose
	EU, 
	
	@SerializedName("NA")
	@Expose
	NA, 
	
	@SerializedName("OC")
	@Expose
	OC, 
	
	@SerializedName("SA")
	@Expose
	SA
}
