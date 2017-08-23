package org.paasfinder.paasalyser.statistics.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class SimpleResultLong {

	private String key;
	private long value;

	public SimpleResultLong(String key, long value) {
		super();
		this.key = key;
		this.value = value;
	}

	public SimpleResultLong() {
		super();
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
