package org.paasfinder.paasalyser.report.models;

public class ExtensibleReport {

    private double extensibleProfilesPercent;

    public ExtensibleReport(double extensibleProfilesPercent) {
	super();
	this.extensibleProfilesPercent = extensibleProfilesPercent;
    }

    public double getExtensibleProfilesPercent() {
	return extensibleProfilesPercent;
    }

}
