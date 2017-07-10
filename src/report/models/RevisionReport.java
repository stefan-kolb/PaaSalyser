package report.models;

import java.util.List;
import java.util.Map.Entry;

import report.QuantitativeModel;

/**
 * This class is to determine how old the current revisions are and therefore if
 * most of them are outdated or not.
 */
public class RevisionReport extends QuantitativeModel {

	public RevisionReport(int numberOfProfiles, double mean, double median, double variance, double stdev,
			List<Entry<String, Long>> minFive, List<Entry<String, Long>> topFive) {
		super(numberOfProfiles, mean, median, variance, stdev, minFive, topFive);
	}

}
