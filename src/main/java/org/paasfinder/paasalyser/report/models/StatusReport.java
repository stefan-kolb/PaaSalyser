package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class StatusReport {

	private double alphaPercent = 0.0;
	private double betaPercent = 0.0;
	private double productionPercent = 0.0;

	public StatusReport(double[] percentages) {
		super();
		this.alphaPercent = percentages[0];
		this.betaPercent = percentages[1];
		this.productionPercent = percentages[2];
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

}
