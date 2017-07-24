package report.models;

import java.util.ArrayList;
import java.util.List;

import statistics.models.SimpleResult;

/**
 * This class is to determine how old the current revisions are and therefore if
 * most of them are outdated or not.
 */
public class RevisionReport{
	
	private int numberOfProfiles;
	private double mean;
	private double median;
	private double variance;
	private double stdev;
	private List<SimpleResult> minFive = new ArrayList<>();
	private List<SimpleResult> topFive = new ArrayList<>();
	
	public RevisionReport(int numberOfProfiles, double mean, double median, double variance, double stdev,
			List<SimpleResult> minFive, List<SimpleResult> topFive) {
		super();
		this.numberOfProfiles = numberOfProfiles;
		this.mean = mean;
		this.median = median;
		this.variance = variance;
		this.stdev = stdev;
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

	public List<SimpleResult> getMinFive() {
		return minFive;
	}

	public List<SimpleResult> getTopFive() {
		return topFive;
	}

}
