package report.models;

import java.util.List;

import statistics.models.SimpleResult;

public class PlatformReport {

	private double platformProfilesPercent = 0.0;
	private List<SimpleResult> topPlatforms;

	public PlatformReport(double platformProfilesPercent, List<SimpleResult> topPlatforms) {
		super();
		this.platformProfilesPercent = platformProfilesPercent;
		this.topPlatforms = topPlatforms;
	}

	public double getPlatformProfilesPercent() {
		return platformProfilesPercent;
	}

	public List<SimpleResult> getTopPlatforms() {
		return topPlatforms;
	}

}
