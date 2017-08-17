package org.paasfinder.paasalyser.profile.models;

import org.paasfinder.paasalyser.gsonutility.deserializers.RuntimeDeserializer;

import com.google.gson.annotations.JsonAdapter;

public class Runtime {

	private String language;
	private String[] versions;

	public Runtime(String language, String[] versions) {
		super();
		this.language = language;
		this.versions = versions;
	}

	public String getLanguage() {
		return language;
	}

	public String[] getVersions() {
		return versions;
	}

}