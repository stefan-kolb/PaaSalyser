package org.paasfinder.paasalyser.statistics.report.models;

public class TypeData {

	private long saasCentric = 0;
	private long generic = 0;
	private long iaasCentric = 0;

	public long getSaasCentric() {
		return saasCentric;
	}

	public long getGeneric() {
		return generic;
	}

	public long getIaasCentric() {
		return iaasCentric;
	}

	public void incrementSaasCentric() {
		saasCentric++;
	}

	public void incrementGeneric() {
		generic++;
	}

	public void incrementIaaSCentric() {
		iaasCentric++;
	}

}
