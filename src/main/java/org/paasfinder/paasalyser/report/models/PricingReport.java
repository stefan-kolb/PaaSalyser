package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

@Embedded
public class PricingReport {

	List<SimpleResultLong> numberOfModelsPerProfile;
	List<SimpleResultLong> models;
	List<SimpleResultLong> periods;

	public PricingReport(List<SimpleResultLong> numberOfModelsPerProfile, List<SimpleResultLong> models,
			List<SimpleResultLong> periods) {
		super();
		this.numberOfModelsPerProfile = numberOfModelsPerProfile;
		this.models = models;
		this.periods = periods;
	}

	public PricingReport() {
		super();
	}

	public List<SimpleResultLong> getNumberOfModelsPerProfile() {
		return numberOfModelsPerProfile;
	}

	public List<SimpleResultLong> getModels() {
		return models;
	}

	public List<SimpleResultLong> getPeriods() {
		return periods;
	}
}
