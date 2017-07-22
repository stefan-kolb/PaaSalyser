package report.models;

import java.util.List;
import java.util.Map.Entry;

import report.QualitativeModel;

public class PlatformReport extends QualitativeModel {

	public PlatformReport(long numberOfProfiles, List<Entry<String, Long>> minFive, List<Entry<String, Long>> topFive) {
		super(numberOfProfiles, minFive, topFive);
	}

}
