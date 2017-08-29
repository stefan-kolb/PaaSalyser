package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ScalingReport {

	private double verticalPercent = 0.0;
	private double horizontalPercent = 0.0;
	private double autoPercent = 0.0;

	public ScalingReport(double[] percentages) {
		super();
		this.verticalPercent = percentages[0];
		this.horizontalPercent = percentages[1];
		this.autoPercent = percentages[2];
	}

	public ScalingReport() {
		super();
	}

	public double getVerticalPercent() {
		return verticalPercent;
	}

	public double getHorizontalPercent() {
		return horizontalPercent;
	}

	public double getAutoPercent() {
		return autoPercent;
	}

}
