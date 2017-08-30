package org.paasfinder.paasalyser.profile.models;

import java.util.LinkedList;
import java.util.List;

public class Framework {

	private String name;
	private String runtime;
	private List<String> versions = null;

	public String getName() {
		return name;
	}

	public String getRuntime() {
		return runtime;
	}

	public List<String> getVersions() {
		if (versions != null) {
			return versions;
		} else {
			return new LinkedList<String>();
		}
	}

}