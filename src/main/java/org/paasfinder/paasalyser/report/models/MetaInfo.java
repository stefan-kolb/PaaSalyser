package org.paasfinder.paasalyser.report.models;

import java.time.LocalDate;

public class MetaInfo {

	private LocalDate date;
	private int numberOfProfiles;
	private int numberOfEolProfiles;
	private int numberOfInvalidProfiles;
	private RevisionReport revisionReport;
	private TypeReport typeReport;

	public MetaInfo(LocalDate date, int numberOfProfiles, int numberOfEolProfiles, int numberOfInvalidProfiles,
			RevisionReport revisionReport, TypeReport typeReport) {
		super();
		this.date = date;
		this.numberOfProfiles = numberOfProfiles;
		this.numberOfEolProfiles = numberOfEolProfiles;
		this.numberOfInvalidProfiles = numberOfInvalidProfiles;
		this.revisionReport = revisionReport;
		this.typeReport = typeReport;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getNumberOfInvalidProfiles() {
		return numberOfInvalidProfiles;
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
