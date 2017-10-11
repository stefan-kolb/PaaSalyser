package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultDouble;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

@Embedded
public class InfrastructuresReport {

	private double meanInfrastructuresPerProfile;
	private double medianInfrastructuresPerProfile;
	private double varianceInfrastructuresPerProfile;
	private double stdevInfrastructuresPerProfile;
	private List<SimpleResultLong> topInfrastructuresPerProfile;

	private List<SimpleResultDouble> percentOfProfilesPerContinent;

	private List<SimpleResultLong> topContinents;
	private List<SimpleResultLong> topCountries;
	private List<SimpleResultLong> topRegions;
	private List<SimpleResultLong> topProviders;

	public InfrastructuresReport(List<SimpleResultLong> topInfrastructuresPerProfile,
			QualitativeData infrastructuresPerProfile, List<SimpleResultDouble> percentOfProfilesPerContinent,
			List<SimpleResultLong> topContinents, List<SimpleResultLong> topCountries,
			List<SimpleResultLong> topRegions, List<SimpleResultLong> topProviders) {
		super();
		this.meanInfrastructuresPerProfile = infrastructuresPerProfile.getMean();
		this.medianInfrastructuresPerProfile = infrastructuresPerProfile.getMedian();
		this.varianceInfrastructuresPerProfile = infrastructuresPerProfile.getVariance();
		this.stdevInfrastructuresPerProfile = infrastructuresPerProfile.getStDev();
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
		return meanInfrastructuresPerProfile;
	}

	public double getMedianInfrastrcturesPerProfile() {
		return medianInfrastructuresPerProfile;
	}

	public double getVarianceInfrastrcturesPerProfile() {
		return varianceInfrastructuresPerProfile;
	}

	public double getStdevInfrastrcturesPerProfile() {
		return stdevInfrastructuresPerProfile;
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
