package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultDouble;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

@Embedded
public class InfrastructuresReport {

	private double meanInfrastrcturesPerProfile;
	private double medianInfrastrcturesPerProfile;
	private double varianceInfrastrcturesPerProfile;
	private double stdevInfrastrcturesPerProfile;
	private List<SimpleResultLong> topInfrastructuresPerProfile;

	private List<SimpleResultDouble> percentOfProfilesPerContinent;

	private List<SimpleResultLong> topContinents;
	private List<SimpleResultLong> topCountries;
	private List<SimpleResultLong> topRegions;
	private List<SimpleResultLong> topProviders;

	public InfrastructuresReport(List<SimpleResultLong> topInfrastructuresPerProfile,
			QualitativeData infrastrcturesPerProfile, List<SimpleResultDouble> percentOfProfilesPerContinent,
			List<SimpleResultLong> topContinents, List<SimpleResultLong> topCountries,
			List<SimpleResultLong> topRegions, List<SimpleResultLong> topProviders) {
		super();
		this.meanInfrastrcturesPerProfile = infrastrcturesPerProfile.getMean();
		this.medianInfrastrcturesPerProfile = infrastrcturesPerProfile.getMedian();
		this.varianceInfrastrcturesPerProfile = infrastrcturesPerProfile.getVariance();
		this.stdevInfrastrcturesPerProfile = infrastrcturesPerProfile.getStDev();
		this.topInfrastructuresPerProfile = topInfrastructuresPerProfile;
		this.percentOfProfilesPerContinent = percentOfProfilesPerContinent;
		this.topContinents = topContinents;
		this.topCountries = topCountries;
		this.topRegions = topRegions;
		this.topProviders = topProviders;
	}

	public InfrastructuresReport() {
		super();
	}

	public List<SimpleResultLong> getTopInfrastrcturesPerProfile() {
		return topInfrastructuresPerProfile;
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

	public List<SimpleResultLong> getTopContinents() {
		return topContinents;
	}

	public List<SimpleResultLong> getTopCountries() {
		return topCountries;
	}

	public List<SimpleResultLong> getTopRegions() {
		return topRegions;
	}

	public List<SimpleResultLong> getTopProviders() {
		return topProviders;
	}

}
