package report.models;

import java.util.List;

import statistics.models.SimpleResultLong;

public class ServicesReport {

    private double nativeServicesProfilesPercent;
    private List<SimpleResultLong> topFiveProfilesWithNativeServicesAmount;
    private List<SimpleResultLong> topFiveNativeServices;
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
