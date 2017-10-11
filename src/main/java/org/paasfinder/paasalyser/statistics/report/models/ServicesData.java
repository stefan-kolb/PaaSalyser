package org.paasfinder.paasalyser.statistics.report.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServicesData {

	private long serviceProfiles = 0;
	private Map<String, Long> profilesWithNativeServices;
	private Map<String, Long> nativeServices;
	private Map<String, Long> typesOfNativeServices;

	public ServicesData() {
		super();
		this.profilesWithNativeServices = new HashMap<>();
		this.nativeServices = new HashMap<>();
		this.typesOfNativeServices = new HashMap<>();
	}

	public long getServiceProfiles() {
		return serviceProfiles;
	}

	public void incrementServiceProfiles() {
		serviceProfiles++;
	}

	public void setNativeServices(Map<String, Long> nativeServices) {
		this.nativeServices = nativeServices;
	}

	public Map<String, Long> getProfilesWithNativeServices() {
		return profilesWithNativeServices;
	}

	public void addProfileWithNativeServices(String name, long amount) {
		if (amount > 0)
			profilesWithNativeServices.put(name, amount);
	}

	public Map<String, Long> getNativeServices() {
		return nativeServices;
	}

	public void addNativeService(String name, String type) {
		Objects.requireNonNull(name);

		if (!name.isEmpty()) {
			if (nativeServices.putIfAbsent(name, (long) 1) != null) {
				nativeServices.replace(name, nativeServices.get(name) + 1);
			}
		}
		if (type != null && !type.isEmpty()) {
			if (typesOfNativeServices.putIfAbsent(type, (long) 1) != null) {
				typesOfNativeServices.replace(type, typesOfNativeServices.get(type) + 1);
			}
		}
	}

	public Map<String, Long> getTypesOfNativeServices() {
		return typesOfNativeServices;
	}

}
