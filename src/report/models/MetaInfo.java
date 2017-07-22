package report.models;

public class MetaInfo {
	
	private RevisionReport revisionReport;
	private TypeReport typeReport;
	
	public MetaInfo(RevisionReport revisionReport, TypeReport typeReport) {
		super();
		this.revisionReport = revisionReport;
		this.typeReport = typeReport;
	}

	public RevisionReport getRevisionReport() {
		return revisionReport;
	}

	public TypeReport getTypeReport() {
		return typeReport;
	}

}
