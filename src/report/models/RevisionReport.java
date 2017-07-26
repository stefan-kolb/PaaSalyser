package report.models;

import java.util.ArrayList;
import java.util.List;

import statistics.models.SimpleResultLong;

/**
 * This class is to determine how old the current revisions are and therefore if
 * most of them are outdated or not.
 */
public class RevisionReport {

	private int numberOfProfiles;
	private double mean;
	private double median;
	private double variance;
	private double stdev;
	private List<SimpleResultLong> minFive = new ArrayList<>();
	private List<SimpleResultLong> topFive = new ArrayList<>();

	public RevisionReport(int numberOfProfiles,
			QualitativeData qualitativeData, List<SimpleResultLong> minFive,
			List<SimpleResultLong> topFive) {
		super();
		this.numberOfProfiles = numberOfProfiles;
		this.mean = qualitativeData.getMean();
		this.median = qualitativeData.getMedian();
		this.variance = qualitativeData.getVariance();
		this.stdev = qualitativeData.getStDev();
		this.minFive = minFive;
		this.topFive = topFive;
	}

	public int getNumberOfProfiles() {
		return numberOfProfiles;
	}

	public double getMean() {
		return mean;
	}

	public double getMedian() {
		return median;
	}

	public double getVariance() {
		return variance;
	}

	public double getStdev() {
		return stdev;
	}

	public List<SimpleResultLong> getMinFive() {
		return minFive;
	}

	public List<SimpleResultLong> getTopFive() {
		return topFive;
	}

}
