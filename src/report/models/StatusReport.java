package report.models;

import java.util.List;
import java.util.Map.Entry;

import report.QualitativeModel;

/**
 * This class is to determine how many of each status are in present in the data
 * set.
 */
public class StatusReport extends QualitativeModel {

	private long production;
	private long alpha;
	private long beta;

	public StatusReport(long numberOfProfiles, long production, long alpha, long beta,
			List<Entry<String, Long>> minFive, List<Entry<String, Long>> topFive) {
		super(numberOfProfiles, minFive, topFive);
		this.production = production;
		this.alpha = alpha;
		this.beta = beta;
	}

	public long getProduction() {
		return production;
	}

	public long getAlpha() {
		return alpha;
	}

	public long getBeta() {
		return beta;
	}

}
