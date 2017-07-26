package report.models;

import java.util.List;

import statistics.models.SimpleResultLong;

public class PlatformReport {

	private double platformProfilesPercent = 0.0;
	private List<SimpleResultLong> topPlatforms;

	public PlatformReport(double platformProfilesPercent, List<SimpleResultLong> topPlatforms) {
		super();
		this.platformProfilesPercent = platformProfilesPercent;
		this.topPlatforms = topPlatforms;
	}

	public double getPlatformProfilesPercent() {
		return platformProfilesPercent;
	}

	public List<SimpleResultLong> getTopPlatforms() {
		return topPlatforms;
	}

}
