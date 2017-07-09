package report.models;

import java.util.List;
import java.util.Map.Entry;

import report.models.supermodels.QualitativeModel;

public class TypeReport extends QualitativeModel {

	private long saasCentric;
	private long generic;
	private long iaasCentric;

	public TypeReport(long numberOfProfiles, long saasCentric, long generic, long iaasCentric,
			List<Entry<String, Long>> minFive, List<Entry<String, Long>> topFive) {
		super(numberOfProfiles, minFive, topFive);
		this.saasCentric = saasCentric;
		this.generic = generic;
		this.iaasCentric = iaasCentric;
	}

	public long getSaasCentric() {
		return saasCentric;
	}

	public long getGeneric() {
		return generic;
	}

	public long getIaasCentric() {
		return iaasCentric;
	}

}
