package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

/**
 * This class is to determine how the PaaS vendor landscape is currently setup
 * regarding types of services. The profile can either be set up as a generic
 * PaaS or more towards IaaS or SaaS.
 */
@Embedded
public class TypeReport {

	private double saasCentricPercent = 0.0;
	private double genericPercent = 0.0;
	private double iaasCentricPercent = 0.0;

	public TypeReport(double saasCentricPercent, double genericPercent, double iaasCentricPercent) {
		super();
		this.saasCentricPercent = saasCentricPercent;
		this.genericPercent = genericPercent;
		this.iaasCentricPercent = iaasCentricPercent;
	}

	public TypeReport() {
		super();
	}

	public double getSaasCentricPercent() {
		return saasCentricPercent;
	}

	public double getGenericPercent() {
		return genericPercent;
	}

	public double getIaasCentricPercent() {
		return iaasCentricPercent;
	}

}
