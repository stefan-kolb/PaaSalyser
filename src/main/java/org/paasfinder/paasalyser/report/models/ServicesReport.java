package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

@Embedded
public class ServicesReport {

	private double nativeServicesProfilesPercent;
	@Embedded
	private List<SimpleResultLong> topFiveProfilesWithNativeServicesAmount;
	@Embedded
	private List<SimpleResultLong> topFiveNativeServices;
	@Embedded
	private List<SimpleResultLong> topFiveTypesOfServices;

	public ServicesReport(double nativeServicesProfilesPercent,
			List<SimpleResultLong> topFiveProfilesWithNativeServicesAmount,
			List<SimpleResultLong> topFiveNativeServices, List<SimpleResultLong> topFiveTypesOfServices) {
		super();
		this.nativeServicesProfilesPercent = nativeServicesProfilesPercent;
		this.topFiveProfilesWithNativeServicesAmount = topFiveProfilesWithNativeServicesAmount;
		this.topFiveNativeServices = topFiveNativeServices;
		this.topFiveTypesOfServices = topFiveTypesOfServices;
	}

	public ServicesReport() {
		super();
	}

	public double getNativeServicesProfilesPercent() {
		return nativeServicesProfilesPercent;
	}

	public List<SimpleResultLong> getTopFiveProfilesWithNativeServicesAmount() {
		return topFiveProfilesWithNativeServicesAmount;
	}

	public List<SimpleResultLong> getTopFiveNativeServices() {
		return topFiveNativeServices;
	}

	public List<SimpleResultLong> getTopFiveTypesOfServices() {
		return topFiveTypesOfServices;
	}
}
