package report.models;

import java.util.List;

import statistics.models.SimpleResultLong;

public class StatusReport {

    // Status Statistics
    private List<SimpleResultLong> topStatus;

    // StatusSince Statistics
    // TODO
    private double meanStatusSince;
    private double medianStatusSince;
    private double varianceStatusSince;
    private double stdevStatusSince;
    private List<SimpleResultLong> topFiveStatusSince;
    private List<SimpleResultLong> minFiveStatusSince;

    public StatusReport(List<SimpleResultLong> topStatus,
	    QualitativeData qualitativeData,
	    List<SimpleResultLong> topFiveStatusSince,
	    List<SimpleResultLong> minFiveStatusSince) {
	super();
	this.topStatus = topStatus;
	this.meanStatusSince = qualitativeData.getMean();
	this.medianStatusSince = qualitativeData.getMedian();
	this.varianceStatusSince = qualitativeData.getVariance();
	this.stdevStatusSince = qualitativeData.getStDev();
	this.topFiveStatusSince = topFiveStatusSince;
	this.minFiveStatusSince = minFiveStatusSince;
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

    public List<SimpleResultLong> getTopFiveStatusSince() {
	return topFiveStatusSince;
    }

    public List<SimpleResultLong> getMinFiveStatusSince() {
	return minFiveStatusSince;
    }

}
