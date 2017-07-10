package report.models;

import java.util.List;
import java.util.Map.Entry;

import report.QuantitativeModel;

/**
 * This class is to determine how many of all profiles of the current data set
 * even have compliances.
 */
public class OverallComplianceReport extends QuantitativeModel {

	private double percentWithCompliance;
	
	public OverallComplianceReport(int numberOfProfiles, double mean, double median, double variance, double stdev,
			List<Entry<String, Long>> minFive, List<Entry<String, Long>> topFive, double percentWithCompliance) {
		super(numberOfProfiles, mean, median, variance, stdev, minFive, topFive);
		this.percentWithCompliance = percentWithCompliance;
	}

	public double getPercentWithCompliance() {
		return percentWithCompliance;
	}

}
