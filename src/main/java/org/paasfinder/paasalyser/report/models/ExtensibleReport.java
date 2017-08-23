package org.paasfinder.paasalyser.report.models;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ExtensibleReport {

	private double extensibleProfilesPercent;

	public ExtensibleReport(double extensibleProfilesPercent) {
		super();
		this.extensibleProfilesPercent = extensibleProfilesPercent;
	}

	public ExtensibleReport() {
		super();
	}

	public double getExtensibleProfilesPercent() {
		return extensibleProfilesPercent;
	}

}
