package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class HostingReport {

	private double privateHostingPercent = 0.0;
	private double publicHostingPercent = 0.0;
	private double virtualPrivateHostingPercent = 0.0;

	public HostingReport(double[] percentages) {
		super();
		this.privateHostingPercent = percentages[0];
		this.publicHostingPercent = percentages[1];
		this.virtualPrivateHostingPercent = percentages[2];
	}

	public HostingReport() {
		super();
	}

	public double getPrivateHostingPercent() {
		return privateHostingPercent;
	}

	public double getPublicHostingPercent() {
		return publicHostingPercent;
	}

	public double getVirtualPrivateHostingPercent() {
		return virtualPrivateHostingPercent;
	}

}
