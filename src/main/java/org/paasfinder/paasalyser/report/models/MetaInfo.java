package org.paasfinder.paasalyser.report.models;

public class MetaInfo {

	private int numberOfProfiles;
	private int numberOfEolProfiles;
	private RevisionReport revisionReport;
	private TypeReport typeReport;

	public MetaInfo(int numberOfProfiles, int numberOfEolProfiles, RevisionReport revisionReport, TypeReport typeReport) {
		super();
		this.numberOfProfiles = numberOfProfiles;
		this.numberOfEolProfiles = numberOfEolProfiles;
		this.revisionReport = revisionReport;
		this.typeReport = typeReport;
	}

	public int getNumberOfProfiles() {
		return numberOfProfiles;
	}

	public int getNumberOfEolProfiles() {
		return numberOfEolProfiles;
	}

	public RevisionReport getRevisionReport() {
		return revisionReport;
	}

	public TypeReport getTypeReport() {
		return typeReport;
	}

}
