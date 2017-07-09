package report.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QosReport {

	private int numberOfProfiles;
	private double mean;
	private double median;
	private double variance;
	private double stdev;
	private List<Map.Entry<String, Double>> minFive = new ArrayList<>();
	private List<Map.Entry<String, Double>> topFive = new ArrayList<>();
	
	public QosReport(int numberOfProfiles, double mean, double median, double variance, double stdev,
			List<Entry<String, Double>> minFive, List<Entry<String, Double>> topFive) {
		super();
		this.numberOfProfiles = numberOfProfiles;
		this.mean = mean;
		this.median = median;
		this.variance = variance;
		this.stdev = stdev;
		minFive.addAll(minFive);
		topFive.addAll(topFive);
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

	public List<Map.Entry<String, Double>> getMinFive() {
		return minFive;
	}

	public List<Map.Entry<String, Double>> getTopFive() {
		return topFive;
	}

}
