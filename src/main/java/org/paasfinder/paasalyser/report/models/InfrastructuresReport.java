package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.paasfinder.paasalyser.statistics.report.models.SimpleResultDouble;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

public class InfrastructuresReport {

	private double meanInfrastrcturesPerProfile;
	private double medianInfrastrcturesPerProfile;
	private double varianceInfrastrcturesPerProfile;
	private double stdevInfrastrcturesPerProfile;
	private List<SimpleResultLong> topFiveInfrastrcturesPerProfile;

	private List<SimpleResultDouble> percentOfProfilesPerContinent;
	private List<SimpleResultLong> topFiveContinents;
	private List<SimpleResultLong> topFiveCountries;
	private List<SimpleResultLong> topFiveRegions;
	private List<SimpleResultLong> topFiveProviders;

	public InfrastructuresReport(List<SimpleResultLong> topFiveInfrastrcturesPerProfile,
			QualitativeData infrastrcturesPerProfile, List<SimpleResultDouble> percentOfProfilesPerContinent,
			List<SimpleResultLong> topFiveContinents, List<SimpleResultLong> topFiveCountries,
			List<SimpleResultLong> topFiveRegions, List<SimpleResultLong> topFiveProviders) {
		super();
		this.meanInfrastrcturesPerProfile = infrastrcturesPerProfile.getMean();
		this.medianInfrastrcturesPerProfile = infrastrcturesPerProfile.getMedian();
		this.varianceInfrastrcturesPerProfile = infrastrcturesPerProfile.getVariance();
		this.stdevInfrastrcturesPerProfile = infrastrcturesPerProfile.getStDev();
		this.topFiveInfrastrcturesPerProfile = topFiveInfrastrcturesPerProfile;
		this.percentOfProfilesPerContinent = percentOfProfilesPerContinent;
		this.topFiveContinents = topFiveContinents;
		this.topFiveCountries = topFiveCountries;
		this.topFiveRegions = topFiveRegions;
		this.topFiveProviders = topFiveProviders;
	}

	public List<SimpleResultLong> getTopFiveInfrastrcturesPerProfile() {
		return topFiveInfrastrcturesPerProfile;
	}

	public double getMeanInfrastrcturesPerProfile() {
		return meanInfrastrcturesPerProfile;
	}

	public double getMedianInfrastrcturesPerProfile() {
		return medianInfrastrcturesPerProfile;
	}

	public double getVarianceInfrastrcturesPerProfile() {
		return varianceInfrastrcturesPerProfile;
	}

	public double getStdevInfrastrcturesPerProfile() {
		return stdevInfrastrcturesPerProfile;
	}

	public List<SimpleResultDouble> getPercentOfProfilesPerContinent() {
		return percentOfProfilesPerContinent;
	}

	public List<SimpleResultLong> getTopFiveContinents() {
		return topFiveContinents;
	}

	public List<SimpleResultLong> getTopFiveCountries() {
		return topFiveCountries;
	}

	public List<SimpleResultLong> getTopFiveRegions() {
		return topFiveRegions;
	}

	public List<SimpleResultLong> getTopFiveProviders() {
		return topFiveProviders;
	}

}