package report.models;

import java.util.List;

import statistics.models.SimpleResult;

public class StatusReport {

	// Status Statistics
	private List<SimpleResult> topStatus;

	// StatusSince Statistics
	private double meanStatusSince;
	private double medianStatusSince;
	private double varianceStatusSince;
	private double stdevStatusSince;
	private List<SimpleResult> topFiveStatusSince;
	private List<SimpleResult> minFiveStatusSince;

	public StatusReport(List<SimpleResult> topStatus, double meanStatusSince, double medianStatusSince,
			double varianceStatusSince, double stdevStatusSince, List<SimpleResult> topFiveStatusSince,
			List<SimpleResult> minFiveStatusSince) {
		super();
		this.topStatus = topStatus;
		this.meanStatusSince = meanStatusSince;
		this.medianStatusSince = medianStatusSince;
		this.varianceStatusSince = varianceStatusSince;
		this.stdevStatusSince = stdevStatusSince;
		this.topFiveStatusSince = topFiveStatusSince;
		this.minFiveStatusSince = minFiveStatusSince;
	}

	public List<SimpleResult> getTopStatus() {
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

	public List<SimpleResult> getTopFiveStatusSince() {
		return topFiveStatusSince;
	}

	public List<SimpleResult> getMinFiveStatusSince() {
		return minFiveStatusSince;
	}

}
