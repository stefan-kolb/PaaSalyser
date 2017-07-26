package statistics.models;

import java.util.HashMap;
import java.util.Map;

public class ServicesData {

    private Map<String, Long> nativeServices;
    private Map<String, Long> typesOfNativeServices;

    public ServicesData() {
	super();
	this.nativeServices = new HashMap<>();
	this.typesOfNativeServices = new HashMap<>();
    }

    public Map<String, Long> getNativeServices() {
	return nativeServices;
    }

    public void addNativeService(String name, String type) {
	if (nativeServices.putIfAbsent(name, (long) 1) != null) {
	    nativeServices.replace(name, nativeServices.get(name) + 1);
	}
	
	String key = name + " (" + type + ")";
	if (nativeServices.putIfAbsent(key, (long) 1) != null) {
	    nativeServices.replace(key, nativeServices.get(key) + 1);
	}
    }

    public Map<String, Long> getTypesOfNativeServices() {
	return typesOfNativeServices;
    }

}
