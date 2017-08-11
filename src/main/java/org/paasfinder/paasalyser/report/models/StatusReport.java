package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.paasfinder.paasalyser.statistics.models.SimpleResultLong;

public class StatusReport {

	// Status Statistics
	private List<SimpleResultLong> topStatus;

	// StatusSince Statistics
	private double meanStatusSince;
	private double medianStatusSince;
	private double varianceStatusSince;
	private double stdevStatusSince;
	private List<SimpleResultLong> oldestStatusSince;
	private List<SimpleResultLong> youngestStatusSince;

	public StatusReport(List<SimpleResultLong> topStatus, QualitativeData qualitativeData,
			List<SimpleResultLong> topFiveStatusSince, List<SimpleResultLong> minFiveStatusSince) {
		super();
		this.topStatus = topStatus;
		this.meanStatusSince = qualitativeData.getMean();
		this.medianStatusSince = qualitativeData.getMedian();
		this.varianceStatusSince = qualitativeData.getVariance();
		this.stdevStatusSince = qualitativeData.getStDev();
		this.oldestStatusSince = topFiveStatusSince;
		this.youngestStatusSince = minFiveStatusSince;
	}

	public List<SimpleResultLong> getTopStatus() {
		return topStatus;
	}

	public double getMeanStatusSince() {
		return meanStatusSince;
	}

	public double getMedianStatusSince() {
		return medianStatusSince;
	}

	public double getVarianceStatusSince() {
		return varianceStatusSince;
	}

	public double getStdevStatusSince() {
		return stdevStatusSince;
	}

	public List<SimpleResultLong> getOldestStatusSince() {
		return oldestStatusSince;
	}

	public List<SimpleResultLong> getYoungestStatusSince() {
		return youngestStatusSince;
	}

}
