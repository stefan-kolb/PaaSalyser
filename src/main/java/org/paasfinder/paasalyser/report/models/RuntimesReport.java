package org.paasfinder.paasalyser.report.models;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultDouble;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

@Embedded
public class RuntimesReport {

	// Number of runtimes per profile
	private long languageSpecific = 0;
	private long polyglot = 0;
	private double meanNumberOfRuntimes;
	private double medianNumberOfRuntimes;
	private double varianceNumberOfRuntimes;
	private double stdevNumberOfRuntimes;

	// Number of runtimes per profile
	private List<SimpleResultLong> topNumberOfRuntimes;

	// Share of Runtimes in profiles
	private List<SimpleResultDouble> runtimesShare;

	public RuntimesReport(long languageSpecific, long polyglot, QualitativeData profileNumberOfRuntimesData,
			List<SimpleResultLong> topNumberOfRuntimes, List<SimpleResultDouble> runtimesShare) {
		this.languageSpecific = languageSpecific;
		this.polyglot = polyglot;
		this.meanNumberOfRuntimes = profileNumberOfRuntimesData.getMean();
		this.medianNumberOfRuntimes = profileNumberOfRuntimesData.getMedian();
		this.varianceNumberOfRuntimes = profileNumberOfRuntimesData.getVariance();
		this.stdevNumberOfRuntimes = profileNumberOfRuntimesData.getStDev();
		this.topNumberOfRuntimes = topNumberOfRuntimes;
		this.runtimesShare = runtimesShare;
	}

	public RuntimesReport() {
		super();
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

	public List<SimpleResultLong> getTopNumberOfRuntimes() {
		return topNumberOfRuntimes;
	}

	public List<SimpleResultDouble> getRuntimesShare() {
		return runtimesShare;
	}

}
