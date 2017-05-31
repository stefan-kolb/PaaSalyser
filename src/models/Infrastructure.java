package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Infrastructure {
	
	@SerializedName("continent")
	@Expose
	private String continent;
	
	@SerializedName("country")
	@Expose
	private String country;
	
	@SerializedName("region")
	@Expose
	private String region;
	
	@SerializedName("region_code")
	@Expose
	private String region_code;
	
	@SerializedName("provider")
	@Expose
	private String provider;

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion_code() {
		return region_code;
	}

	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}
