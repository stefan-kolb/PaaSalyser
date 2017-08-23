package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

@Embedded
public class PlatformReport {

	private double platformProfilesPercent = 0.0;
	@Embedded
	private List<SimpleResultLong> topPlatforms;

	public PlatformReport(double platformProfilesPercent, List<SimpleResultLong> topPlatforms) {
		super();
		this.platformProfilesPercent = platformProfilesPercent;
		this.topPlatforms = topPlatforms;
	}

	public PlatformReport() {
		super();
	}

	public double getPlatformProfilesPercent() {
		return platformProfilesPercent;
	}

	public List<SimpleResultLong> getTopPlatforms() {
		return topPlatforms;
	}

}
