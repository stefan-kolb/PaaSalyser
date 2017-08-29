package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

@Embedded
public class StatusReport {

	private double alphaPercent = 0.0;
	private double betaPercent = 0.0;
	private double productionPercent = 0.0;

	// StatusSince Statistics
	private double meanStatusSince;
	private double medianStatusSince;
	private double varianceStatusSince;
	private double stdevStatusSince;

	private List<SimpleResultLong> oldestStatusSince;
	private List<SimpleResultLong> youngestStatusSince;

	public StatusReport(double[] percentages, QualitativeData qualitativeData,
			List<SimpleResultLong> topFiveStatusSince, List<SimpleResultLong> minFiveStatusSince) {
		super();
		this.alphaPercent = percentages[0];
		this.betaPercent = percentages[1];
		this.productionPercent = percentages[2];
		this.meanStatusSince = qualitativeData.getMean();
		this.medianStatusSince = qualitativeData.getMedian();
		this.varianceStatusSince = qualitativeData.getVariance();
		this.stdevStatusSince = qualitativeData.getStDev();
		this.oldestStatusSince = topFiveStatusSince;
		this.youngestStatusSince = minFiveStatusSince;
	}

	public StatusReport() {
		super();
	}

	public double getAlphaPercent() {
		return alphaPercent;
	}

	public double getBetaPercent() {
		return betaPercent;
	}

	public double getProductionPercent() {
		return productionPercent;
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
