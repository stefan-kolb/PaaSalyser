package org.paasfinder.paasalyser.profile.models;

public class NativeService {

	private String name;
	private String type;
	private String description;
	private String[] versions;

	public NativeService(String name, String type, String description, String[] versions) {
		super();
		this.name = name;
		this.type = type;
		this.description = description;
		this.versions = versions;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public String[] getVersions() {
		return versions;
	}

}
