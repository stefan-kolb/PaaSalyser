package report.models;

import java.util.List;
import java.util.Map.Entry;

import report.models.supermodels.QualitativeModel;

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
