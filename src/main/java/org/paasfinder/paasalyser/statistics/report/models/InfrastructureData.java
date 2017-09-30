package org.paasfinder.paasalyser.statistics.report.models;

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

		// Initialise all possible continents
		profileContinents = new HashMap<>();
		profileContinents.put("NA", (long) 0);
		profileContinents.put("EU", (long) 0);
		profileContinents.put("AS", (long) 0);
		profileContinents.put("OC", (long) 0);
		profileContinents.put("SA", (long) 0);
		profileContinents.put("AF", (long) 0);

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
		// null- and empty-checks have already been done

		infrastructuresPerProfile.putIfAbsent(name, (long) infrastructures.length);

		boolean na = false, eu = false, as = false, oc = false, sa = false, af = false;

		for (Infrastructure infra : infrastructures) {
			String continent = infra.getContinent();

			if (continent == null || continent.isEmpty() || continent.equals("null") || continent.equals("empty"))
				return;

			if (continent.equals("NA")) {
				na = true;
			} else if (continent.equals("EU")) {
				eu = true;
			} else if (continent.equals("AS")) {
				as = true;
			} else if (continent.equals("OC")) {
				oc = true;
			} else if (continent.equals("SA")) {
				sa = true;
			} else if (continent.equals("AF")) {
				af = true;
			}
		}

		if (na) {
			profileContinents.replace("NA", profileContinents.get("NA") + 1);
		}
		if (eu) {
			profileContinents.replace("EU", profileContinents.get("EU") + 1);
		}
		if (as) {
			profileContinents.replace("AS", profileContinents.get("AS") + 1);
		}
		if (oc) {
			profileContinents.replace("OC", profileContinents.get("OC") + 1);
		}
		if (sa) {
			profileContinents.replace("SA", profileContinents.get("SA") + 1);
		}
		if (af) {
			profileContinents.replace("AF", profileContinents.get("AF") + 1);
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

	@Override
	public String toString() {
		return "InfrastructureData [infrastructuresPerProfile=" + infrastructuresPerProfile + ", profileContinents="
				+ profileContinents + ", continent=" + continent + ", country=" + country + ", region=" + region
				+ ", provider=" + provider + "]";
	}

}
