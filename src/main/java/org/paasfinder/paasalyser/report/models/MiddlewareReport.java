package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class MiddlewareReport {

	private final String info = "Not implemented yet.";

	public MiddlewareReport() {
	}

	public String getInfo() {
		return info;
	}

}
