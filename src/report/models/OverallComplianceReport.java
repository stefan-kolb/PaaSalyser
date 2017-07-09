package report.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import report.models.supermodels.QualitativeModel;

public class OverallComplianceReport extends QualitativeModel {
	
	List<Map.Entry<String, Long>> profilesWithCompliances = new ArrayList<>();

	public OverallComplianceReport(long numberOfProfiles, List<Entry<String, Long>> minFive,
			List<Entry<String, Long>> topFive, List<Entry<String, Long>> profilesWithCompliances) {
		super(numberOfProfiles, minFive, topFive);
		this.profilesWithCompliances = profilesWithCompliances;
	}

	public List<Map.Entry<String, Long>> getEntries() {
		return profilesWithCompliances;
	}

}
