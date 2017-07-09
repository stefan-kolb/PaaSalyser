package report.models.supermodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class QualitativeModel {

	private long numberOfProfiles;
	private List<Map.Entry<String, Long>> minFive = new ArrayList<>();
	private List<Map.Entry<String, Long>> topFive = new ArrayList<>();

	public QualitativeModel(long numberOfProfiles, List<Entry<String, Long>> minFive,
			List<Entry<String, Long>> topFive) {
		super();
		this.numberOfProfiles = numberOfProfiles;
		minFive.addAll(minFive);
		topFive.addAll(topFive);
	}

	public long getNumberOfProfiles() {
		return numberOfProfiles;
	}

	public List<Map.Entry<String, Long>> getMinFive() {
		return minFive;
	}

	public List<Map.Entry<String, Long>> getTopFive() {
		return topFive;
	}
}
