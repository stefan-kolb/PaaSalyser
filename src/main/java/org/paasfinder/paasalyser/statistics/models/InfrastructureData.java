package org.paasfinder.paasalyser.statistics.models;

import java.util.HashMap;
import java.util.Map;

import org.paasfinder.paasalyser.profile.models.Infrastructure;

public class InfrastructureData {

	private Map<String, Long> infrastructuresPerProfile;
	private Map<String, Long> profileContinents;

	private Map<String, Long> continent;
	private Map<String, Long> country;
	private Map<String, Long> region;
	private Map<String, Long> provider;

	public InfrastructureData() {
		super();
		infrastructuresPerProfile = new HashMap<>();
		profileContinents = new HashMap<>();
		continent = new HashMap<>();
		country = new HashMap<>();
		region = new HashMap<>();
		provider = new HashMap<>();
	}

	public Map<String, Long> getInfrastructuresPerProfile() {
		return infrastructuresPerProfile;
	}

	public Map<String, Long> getProfileContinents() {
		return profileContinents;
	}

	public Map<String, Long> getContinent() {
		return continent;
	}

	public Map<String, Long> getCountry() {
		return country;
	}

	public Map<String, Long> getRegion() {
		return region;
	}

	public Map<String, Long> getProvider() {
		return provider;
	}

	public void addInfraStructureProfile(String name, Infrastructure[] infrastructures) {
		if (name == null || infrastructures == null) {
			return;
		}
		if (name.isEmpty() || infrastructures.length == 0) {
			return;
		}

		infrastructuresPerProfile.putIfAbsent(name, (long) infrastructures.length);

		for (Infrastructure infra : infrastructures) {
			String continent = infra.getContinent();
			if (continent == null || continent.isEmpty())
				return;
			if (profileContinents.putIfAbsent(continent, (long) 1) != null)
				profileContinents.replace(continent, profileContinents.get(continent) + 1);

		}

	}

	public void addContinent(String name) {
		if (name == null || name.isEmpty())
			return;
		if (continent.putIfAbsent(name, (long) 1) != null)
			continent.replace(name, continent.get(name) + 1);
	}

	public void addCountry(String name) {
		if (name == null || name.isEmpty())
			return;
		if (country.putIfAbsent(name, (long) 1) != null)
			country.replace(name, country.get(name) + 1);
	}

	public void addRegion(String name) {
		if (name == null || name.isEmpty())
			return;
		if (region.putIfAbsent(name, (long) 1) != null)
			region.replace(name, region.get(name) + 1);
	}

	public void addProvider(String name) {
		if (name == null || name.isEmpty())
			return;
		if (provider.putIfAbsent(name, (long) 1) != null)
			provider.replace(name, provider.get(name) + 1);
	}

}
