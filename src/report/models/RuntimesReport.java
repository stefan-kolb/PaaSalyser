package report.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import statistics.models.SimpleResult;

public class RuntimesReport {

	// Number of runtimes per profile
	private long languageSpecific = 0;
	private long polyglot = 0;
	private double meanNumberOfRuntimes;
	private double medianNumberOfRuntimes;
	private double varianceNumberOfRuntimes;
	private double stdevNumberOfRuntimes;
	private List<SimpleResult> topFiveNumberOfRuntimes = new ArrayList<>();

	public RuntimesReport(long languageSpecific, long polyglot, double[] runtimesPerProfile) {

	}

	// Percent of Profiles with specific runtime
	private List<Map.Entry<String, Double>> runtimes;

}
