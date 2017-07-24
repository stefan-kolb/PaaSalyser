package report.models;

import java.util.List;

import statistics.models.SimpleResult;

/**
 * This class is to determine how the PaaS vendor landscape is currently setup
 * regarding types of services. The profile can either be set up as a generic
 * PaaS or more towards IaaS or SaaS.
 */
public class TypeReport {

	private List<SimpleResult> topTypes;

	public TypeReport(List<SimpleResult> topTypes) {
		super();
		this.topTypes = topTypes;
	}

	public List<SimpleResult> getTopTypes() {
		return topTypes;
	}

}
