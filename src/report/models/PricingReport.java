package report.models;

import java.util.ArrayList;
import java.util.List;

import statistics.models.SimpleResult;

public class PricingReport {
	List<SimpleResult> numberOfModelsPerProfile = new ArrayList<>();
	List<SimpleResult> models = new ArrayList<>();
	List<SimpleResult> periods = new ArrayList<>();

	public PricingReport(List<SimpleResult> numberOfModelsPerProfile, List<SimpleResult> models,
			List<SimpleResult> periods) {
		super();
		this.numberOfModelsPerProfile = numberOfModelsPerProfile;
		this.models = models;
		this.periods = periods;
	}

	public List<SimpleResult> getNumberOfModelsPerProfile() {
		return numberOfModelsPerProfile;
	}

	public List<SimpleResult> getModels() {
		return models;
	}

	public List<SimpleResult> getPeriods() {
		return periods;
	}
}
