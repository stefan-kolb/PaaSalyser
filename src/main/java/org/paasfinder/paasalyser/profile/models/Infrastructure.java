package org.paasfinder.paasalyser.profile.models;

public class Infrastructure {

	private String continent;
	private String country;
	private String region;
	private String region_code;
	private String provider;

	public Infrastructure(String continent, String country, String region, String region_code, String provider) {
		super();
		this.continent = continent;
		this.country = country;
		this.region = region;
		this.region_code = region_code;
		this.provider = provider;
	}

	public String getContinent() {
		return continent;
	}

	public String getCountry() {
		return country;
	}

	public String getRegion() {
		return region;
	}

	public String getRegion_code() {
		return region_code;
	}

	public String getProvider() {
		return provider;
	}
}
