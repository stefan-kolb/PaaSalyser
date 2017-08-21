package org.paasfinder.paasalyser.report.models;

public class MetaInfo {

	private int numberOfProfiles;
	private int numberOfEolProfiles;
	private int numberOfInvalidProfiles;
	private RevisionReport revisionReport;
	private TypeReport typeReport;

	public MetaInfo(int numberOfProfiles, int numberOfEolProfiles, int numberOfInvalidProfiles,
			RevisionReport revisionReport, TypeReport typeReport) {
		super();
		this.numberOfProfiles = numberOfProfiles;
		this.numberOfEolProfiles = numberOfEolProfiles;
		this.numberOfInvalidProfiles = numberOfInvalidProfiles;
		this.revisionReport = revisionReport;
		this.typeReport = typeReport;
	}

	public int getNumberOfProfiles() {
		return numberOfProfiles;
	}

	public int getNumberOfEolProfiles() {
		return numberOfEolProfiles;
	}

	public int getNumberOInvalidProfiles() {
		return numberOfInvalidProfiles;
	}

	public RevisionReport getRevisionReport() {
		return revisionReport;
	}

	public TypeReport getTypeReport() {
		return typeReport;
	}

}
