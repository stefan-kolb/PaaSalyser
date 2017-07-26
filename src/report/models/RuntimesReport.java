package report.models;

import java.util.ArrayList;
import java.util.List;

import statistics.models.SimpleResultDouble;
import statistics.models.SimpleResultLong;

public class RuntimesReport {

    // Number of runtimes per profile
    private long languageSpecific = 0;
    private long polyglot = 0;
    private double meanNumberOfRuntimes;
    private double medianNumberOfRuntimes;
    private double varianceNumberOfRuntimes;
    private double stdevNumberOfRuntimes;
    private List<SimpleResultLong> topFiveNumberOfRuntimes = new ArrayList<>();

    // Amount of Runtimes
    private List<SimpleResultDouble> runtimesShare;

    public RuntimesReport(long languageSpecific, long polyglot,
	    QualitativeData profileNumberOfRuntimesData,
	    List<SimpleResultLong> topFiveNumberOfRuntimes,
	    List<SimpleResultDouble> runtimesShare) {
	this.languageSpecific = languageSpecific;
	this.polyglot = polyglot;
	this.meanNumberOfRuntimes = profileNumberOfRuntimesData.getMean();
	this.medianNumberOfRuntimes = profileNumberOfRuntimesData.getMedian();
	this.varianceNumberOfRuntimes = profileNumberOfRuntimesData
		.getVariance();
	this.stdevNumberOfRuntimes = profileNumberOfRuntimesData.getStDev();
	this.topFiveNumberOfRuntimes = topFiveNumberOfRuntimes;
	this.runtimesShare = runtimesShare;
    }

    public long getLanguageSpecific() {
	return languageSpecific;
    }

    public long getPolyglot() {
	return polyglot;
    }

    public double getMeanNumberOfRuntimes() {
	return meanNumberOfRuntimes;
    }

    public double getMedianNumberOfRuntimes() {
	return medianNumberOfRuntimes;
    }

    public double getVarianceNumberOfRuntimes() {
	return varianceNumberOfRuntimes;
    }

    public double getStdevNumberOfRuntimes() {
	return stdevNumberOfRuntimes;
    }

    public List<SimpleResultLong> getTopFiveNumberOfRuntimes() {
	return topFiveNumberOfRuntimes;
    }

    public List<SimpleResultDouble> getRuntimesShare() {
	return runtimesShare;
    }

}
