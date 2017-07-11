package report.models;

import java.util.List;
import java.util.Map.Entry;

import report.QualitativeModel;

/**
 * This class is to determine how many of each compliance is present in the data
 * set
 */
public class SpecificComplianceReport extends QualitativeModel {

	public SpecificComplianceReport(long numberOfProfiles, List<Entry<String, Long>> minFive,
			List<Entry<String, Long>> topFive) {
		super(numberOfProfiles, minFive, topFive);
	}

}
