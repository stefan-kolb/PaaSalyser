package statistics.models;

import java.util.HashMap;
import java.util.Map;

public class ServicesData {

    private Map<String, Long> profilesWithNativeServices;
    private Map<String, Long> nativeServices;
    private Map<String, Long> typesOfNativeServices;

    public ServicesData() {
	super();
	this.profilesWithNativeServices = new HashMap<>();
	this.nativeServices = new HashMap<>();
	this.typesOfNativeServices = new HashMap<>();
    }

    public Map<String, Long> getProfilesWithNativeServices() {
	return profilesWithNativeServices;
    }

    public void addProfilesWithNativeServices(String name, long amount) {
	if (amount > 0)
	    profilesWithNativeServices.put(name, amount);
    }

    public Map<String, Long> getNativeServices() {
	return nativeServices;
    }

    public void addNativeService(String name, String type) {
	if (name != null) {
	    if (type != null) {
		if (!(name.equals("") || type.equals(""))) {
		    if (typesOfNativeServices.putIfAbsent(type, (long) 1) != null) {
			typesOfNativeServices.replace(type, typesOfNativeServices.get(type) + 1);
		    }

		    String key = name + " (" + type + ")";
		    if (nativeServices.putIfAbsent(key, (long) 1) != null) {
			nativeServices.replace(key, nativeServices.get(key) + 1);
		    }
		}
	    }
	}
    }

    public Map<String, Long> getTypesOfNativeServices() {
	return typesOfNativeServices;
    }

}
