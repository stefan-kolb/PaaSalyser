package report.models;

import java.util.ArrayList;
import java.util.List;

import statistics.models.SimpleResultLong;

public class PricingReport {
	List<SimpleResultLong> numberOfModelsPerProfile = new ArrayList<>();
	List<SimpleResultLong> models = new ArrayList<>();
	List<SimpleResultLong> periods = new ArrayList<>();

	public PricingReport(List<SimpleResultLong> numberOfModelsPerProfile, List<SimpleResultLong> models,
			List<SimpleResultLong> periods) {
		super();
		this.numberOfModelsPerProfile = numberOfModelsPerProfile;
		this.models = models;
		this.periods = periods;
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
