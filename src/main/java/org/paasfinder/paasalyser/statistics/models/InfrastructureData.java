package org.paasfinder.paasalyser.statistics.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void addInfraStructureProfile(String name, List<Infrastructure> infrastructures) {
	if (name != null && infrastructures != null)
	    if (!name.isEmpty() && !infrastructures.isEmpty())
		infrastructuresPerProfile.put(name, (long) infrastructures.size());
	List<String> temp = new ArrayList<>();
	infrastructures.forEach(infra -> {
	    if (!temp.contains(infra.getContinent())) {
		temp.add(infra.getContinent());
	    }
	});
	temp.forEach(element -> {
	    if (profileContinents.putIfAbsent(element, (long) 1) != null)
		profileContinents.replace(element, profileContinents.get(element) + 1);
	});
    }

    public void addContinentProfile(String continent) {
	if (continent != null)
	    if (!continent.isEmpty())
		if (profileContinents.putIfAbsent(continent, (long) 1) != null)
		    profileContinents.replace(continent, profileContinents.get(continent) + 1);
    }

    public void addContinent(String name) {
	if (name != null)
	    if (!name.isEmpty())
		if (continent.putIfAbsent(name, (long) 1) != null)
		    continent.replace(name, continent.get(name) + 1);
    }

    public void addCountry(String name) {
	if (name != null)
	    if (!name.isEmpty())
		if (country.putIfAbsent(name, (long) 1) != null)
		    country.replace(name, country.get(name) + 1);
    }

    public void addRegion(String name) {
	if (name != null)
	    if (!name.isEmpty())
		if (region.putIfAbsent(name, (long) 1) != null)
		    region.replace(name, region.get(name) + 1);
    }

    public void addProvider(String name) {
	if (name != null)
	    if (!name.isEmpty())
		if (provider.putIfAbsent(name, (long) 1) != null)
		    provider.replace(name, provider.get(name) + 1);
    }

}
