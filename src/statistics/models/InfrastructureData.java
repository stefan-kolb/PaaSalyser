package statistics.models;

import java.util.HashMap;
import java.util.Map;

public class InfrastructureData {

    private Map<String, Long> continent;
    private Map<String, Long> country;
    private Map<String, Long> region;
    private Map<String, Long> provider;

    public InfrastructureData() {
	super();
	continent = new HashMap<>();
	country = new HashMap<>();
	region = new HashMap<>();
	provider = new HashMap<>();
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
	System.out.println(name);
	if (name != null)
	    if (!name.isEmpty())
		if (provider.putIfAbsent(name, (long) 1) != null)
		    provider.replace(name, provider.get(name) + 1);
    }

}
