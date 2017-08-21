package org.paasfinder.paasalyser.statistics.report.models;

import java.util.HashMap;
import java.util.Map;

public class StatusData {

	// Status Statistics
	private long alpha = 0;
	private long beta = 0;
	private long eol = 0;
	private long production = 0;

	// StatusSince Statistics
	private Map<String, Long> statusSince = new HashMap<>();

	public long getAlpha() {
		return alpha;
	}

	public void incrementAlpha() {
		alpha++;
	}

	public long getBeta() {
		return beta;
	}

	public void incrementBeta() {
		beta++;
	}

	public long getEol() {
		return eol;
	}

	public void incrementEol() {
		eol++;
	}

	public long getProduction() {
		return production;
	}

	public void incrementProduction() {
		production++;
	}

	public Map<String, Long> getStatusSince() {
		return statusSince;
	}

	public void addStatusSince(String key, long value) {
		statusSince.put(key, value);
	}

}
