package org.paasfinder.paasalyser.statistics.report.models;

public class StatusData {

	private long alpha = 0;
	private long beta = 0;
	private long eol = 0;
	private long production = 0;

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
}
