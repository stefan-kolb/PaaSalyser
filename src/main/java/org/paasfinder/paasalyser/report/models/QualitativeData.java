package org.paasfinder.paasalyser.report.models;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;

public class QualitativeData {

	private double mean;
	private double median;
	private double variance;
	private double stDev;

	public QualitativeData(double[] values) {
		calcValues(values);
	}

	public QualitativeData(Map<String, Long> data) {
		calcValues(data.entrySet().stream().mapToDouble(entry -> entry.getValue().doubleValue()).toArray());
	}

	public QualitativeData(List<Long> data) {
		calcValues(data.stream().mapToDouble(value -> value.doubleValue()).toArray());
	}

	public double getMean() {
		return mean;
	}

	public double getMedian() {
		return median;
	}

	public double getVariance() {
		return variance;
	}

	public double getStDev() {
		return stDev;
	}

	private void calcValues(double[] values) {
		mean = StatUtils.mean(values);
		median = new Median().evaluate(values);
		variance = StatUtils.populationVariance(values);
		stDev = Math.sqrt(variance);
	}
}
