package org.paasfinder.paasalyser.statistics.models;

import java.util.HashMap;
import java.util.Map;

public class PlatformData {

	private long platformProfiles = 0;
	private Map<String, Long> platforms = new HashMap<String, Long>();

	public long getplatformProfiles() {
		return platformProfiles;
	}

	public Map<String, Long> getPlatforms() {
		return platforms;
	}

	public void addPlatform(String name) {
		// Value is always 1 if a platform is found
		if (platforms.putIfAbsent(name, (long) 1) != null) {
			platforms.replace(name, platforms.get(name) + 1);
		}
		platformProfiles++;
	}

}
