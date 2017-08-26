package org.paasfinder.paasalyser.statistics.report.models;

public class ExtensibleData {

	private long _true = 0;

	public long getTrue() {
		return _true;
	}

	public void incrementTrue() {
		_true++;
	}

	@Override
	public String toString() {
		return "ExtensibleData [_true=" + _true + "]";
	}

}
