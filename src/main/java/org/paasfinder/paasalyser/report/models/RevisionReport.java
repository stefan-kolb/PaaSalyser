package org.paasfinder.paasalyser.report.models;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;

/**
 * This class is to determine how old the current revisions are and therefore if
 * most of them are outdated or not.
 */
@Embedded
public class RevisionReport {

	private double mean;
	private double median;
	private double variance;
	private double stdev;
	private List<SimpleResultLong> youngestRevisions = new ArrayList<>();
	private List<SimpleResultLong> oldestRevisions = new ArrayList<>();

	public RevisionReport(QualitativeData qualitativeData, List<SimpleResultLong> minFive,
			List<SimpleResultLong> topFive) {
		super();
		this.mean = qualitativeData.getMean();
		this.median = qualitativeData.getMedian();
		this.variance = qualitativeData.getVariance();
		this.stdev = qualitativeData.getStDev();
		this.youngestRevisions = minFive;
		this.oldestRevisions = topFive;
	}

	public RevisionReport() {
		super();
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

	public double getStdev() {
		return stdev;
	}

	public List<SimpleResultLong> getYoungestRevisions() {
		return youngestRevisions;
	}

	public List<SimpleResultLong> getOldestRevisions() {
		return oldestRevisions;
	}

}
