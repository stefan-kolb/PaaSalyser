package org.paasfinder.paasalyser.report.models;

import java.time.LocalDate;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class MetaInfo {

	private LocalDate date;
	private int numberOfProfiles;
	private int numberOfEolProfiles;
	private int numberOfInvalidProfiles;
	@Embedded
	private RevisionReport revisionReport;
	@Embedded
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

	public MetaInfo() {
		super();
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

	public RevisionReport getRevisionReport() {
		return revisionReport;
	}

	public TypeReport getTypeReport() {
		return typeReport;
	}

}
