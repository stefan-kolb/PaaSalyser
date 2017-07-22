package report.models;

public class MetaInfo {
	
	private int numberOfProfiles;
	private RevisionReport revisionReport;
	private TypeReport typeReport;
	
	public MetaInfo(int numberOfProfiles, RevisionReport revisionReport, TypeReport typeReport) {
		super();
		this.numberOfProfiles = numberOfProfiles;
		this.revisionReport = revisionReport;
		this.typeReport = typeReport;
	}

	public int getNumberOfProfiles(){
		return numberOfProfiles;
	}
	
	public RevisionReport getRevisionReport() {
		return revisionReport;
	}

	public TypeReport getTypeReport() {
		return typeReport;
	}

}
