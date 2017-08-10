package org.paasfinder.paasalyser.statistics.models;

public class SimpleResultLong {

	private String key;
	private long value;

	public SimpleResultLong(String key, long value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public long getValue() {
		return value;
	}

	public void incrementValue() {
		value++;
	}

}
