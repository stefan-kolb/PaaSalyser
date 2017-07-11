package report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QuantitativeModel {

	private int numberOfProfiles;
	private double mean;
	private double median;
	private double variance;
	private double stdev;
	private List<Map.Entry<String, Long>> minFive = new ArrayList<>();
	private List<Map.Entry<String, Long>> topFive = new ArrayList<>();

	public QuantitativeModel(int numberOfProfiles, double mean, double median, double variance, double stdev,
			List<Entry<String, Long>> minFive, List<Entry<String, Long>> topFive) {
		super();
		this.numberOfProfiles = numberOfProfiles;
		this.mean = mean;
		this.median = median;
		this.variance = variance;
		this.stdev = stdev;
		this.minFive.addAll(minFive);
		this.topFive.addAll(topFive);
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

	public List<Map.Entry<String, Long>> getMinFive() {
		return minFive;
	}

	public List<Map.Entry<String, Long>> getTopFive() {
		return topFive;
	}
}
