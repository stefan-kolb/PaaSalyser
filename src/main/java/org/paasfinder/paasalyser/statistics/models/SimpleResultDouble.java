package org.paasfinder.paasalyser.statistics.models;

public class SimpleResultDouble {

	private String key;
	private double value;

	public SimpleResultDouble(String key, double value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public double getValue() {
		return value;
	}

}
