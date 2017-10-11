package org.paasfinder.paasalyser.statistics.report.models;

public class ScalingData {

	private long scalable = 0;
	private long horizontal = 0;
	private long vertical = 0;
	private long auto = 0;

	public long getScalable() {
		return scalable;
	}

	public void incrementScalable() {
		scalable++;
	}

	public long getHorizontal() {
		return horizontal;
	}

	public void incrementHorizontal() {
		horizontal++;
	}

	public long getVertical() {
		return vertical;
	}

	public void incrementVertical() {
		vertical++;
	}

	public long getAuto() {
		return auto;
	}

	public void incrementAuto() {
		auto++;
	}

}
