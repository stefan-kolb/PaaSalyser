package report.models;

import java.util.List;

import java.util.Map.Entry;

import report.QuantitativeModel;

/**
 * This class is to determine how long the profiles are in production in order
 * to tell if the service is free of bugs that may appear in younger versions.
 * These bugs may be resolved in profiles that are longer in the status
 * production.
 */
public class StatusSinceReport extends QuantitativeModel {

	public StatusSinceReport(int numberOfProfiles, double mean, double median, double variance, double stdev,
			List<Entry<String, Long>> minFive, List<Entry<String, Long>> topFive) {
		super(numberOfProfiles, mean, median, variance, stdev, minFive, topFive);
	}

}
