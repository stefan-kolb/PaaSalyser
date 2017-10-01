package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

@Embedded
public class ServicesReport {

	private double nativeServicesProfilesPercent;
	private List<SimpleResultLong> topProfilesWithNativeServicesAmount;
	private List<SimpleResultLong> topNativeServices;
	private List<SimpleResultLong> topTypesOfServices;

	public ServicesReport(double nativeServicesProfilesPercent,
			List<SimpleResultLong> topProfilesWithNativeServicesAmount, List<SimpleResultLong> topNativeServices,
			List<SimpleResultLong> topTypesOfServices) {
		super();
		this.nativeServicesProfilesPercent = nativeServicesProfilesPercent;
		this.topProfilesWithNativeServicesAmount = topProfilesWithNativeServicesAmount;
		this.topNativeServices = topNativeServices;
		this.topTypesOfServices = topTypesOfServices;
	}
	
	public ServicesReport() {
		super();
	}

	public double getNativeServicesProfilesPercent() {
		return nativeServicesProfilesPercent;
	}

	public List<SimpleResultLong> getTopProfilesWithNativeServicesAmount() {
		return topProfilesWithNativeServicesAmount;
	}

	public List<SimpleResultLong> getTopNativeServices() {
		return topNativeServices;
	}

	public List<SimpleResultLong> getTopTypesOfServices() {
		return topTypesOfServices;
	}

}
