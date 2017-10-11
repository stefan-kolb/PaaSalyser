package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ScalingReport {

	private double scalable = 0.0;
	private double verticalPercent = 0.0;
	private double horizontalPercent = 0.0;
	private double autoPercent = 0.0;

	public ScalingReport(double[] percentages) {
		super();
		this.scalable = percentages[0];
		this.verticalPercent = percentages[1];
		this.horizontalPercent = percentages[2];
		this.autoPercent = percentages[3];
	}

	public ScalingReport() {
		super();
	}

	public double getScalablePercent() {
		return scalable;
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
