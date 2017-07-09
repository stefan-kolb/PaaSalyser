package report.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import report.models.supermodels.QuantitativeModel;

public class SpecificComplianceReport extends QuantitativeModel {
	
	List<Map.Entry<String, Long>> compliances = new ArrayList<>();

	public SpecificComplianceReport(int numberOfProfiles, double mean, double median, double variance, double stdev,
			List<Entry<String, Long>> minFive, List<Entry<String, Long>> topFive,
			List<Entry<String, Long>> compliances) {
		super(numberOfProfiles, mean, median, variance, stdev, minFive, topFive);
		this.compliances.addAll(compliances);
	}

}
