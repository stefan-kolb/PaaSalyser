package org.paasfinder.paasalyser.statistics.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class SimpleResultDouble {

	private String key;
	private double value;

	public SimpleResultDouble(String key, double value) {
		super();
		this.key = key;
		this.value = value;
	}

	public SimpleResultDouble() {
		super();
	}

	public String getKey() {
		return key;
	}

	public double getValue() {
		return value;
	}

}
