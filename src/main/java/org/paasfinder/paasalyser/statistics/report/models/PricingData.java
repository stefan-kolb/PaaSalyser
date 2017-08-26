package org.paasfinder.paasalyser.statistics.report.models;

public class PricingData {

	// Model overview
	private long zeroModels = 0;
	private long oneModel = 0;
	private long twoModels = 0;
	private long threeModels = 0;
	private long fourModels = 0;

	// Model data
	private long freeModel = 0;
	private long fixedModel = 0;
	private long meteredModel = 0;
	private long hybridModel = 0;
	private long emptyModel = 0;

	// Period data
	private long dailyPeriod = 0;
	private long monthlyPeriod = 0;
	private long annuallyPariod = 0;
	private long emptyPeriod = 0;

	public long getZeroModels() {
		return zeroModels;
	}

	public void incrementZeroModels() {
		zeroModels++;
	}

	public long getOneModel() {
		return oneModel;
	}

	public void incrementOneModel() {
		oneModel++;
	}

	public long getTwoModels() {
		return twoModels;
	}

	public void incrementTwoModels() {
		twoModels++;
	}

	public long getThreeModels() {
		return threeModels;
	}

	public void incrementThreeModels() {
		threeModels++;
	}

	public long getFourModels() {
		return fourModels;
	}

	public void incrementFourModels() {
		fourModels++;
	}

	public long getFreeModel() {
		return freeModel;
	}

	public void incrementFreeModels() {
		freeModel++;
	}

	public long getFixedModel() {
		return fixedModel;
	}

	public void incrementFixedModels() {
		fixedModel++;
	}

	public long getMeteredModel() {
		return meteredModel;
	}

	public void incrementMeteredModels() {
		meteredModel++;
	}

	public long getHybridModel() {
		return hybridModel;
	}

	public void incrementHybridModels() {
		hybridModel++;
	}

	public long getEmptyModel() {
		return emptyModel;
	}

	public void incrementEmptyModels() {
		emptyModel++;
	}

	public long getDailyPeriod() {
		return dailyPeriod;
	}

	public void incrementDailyPeriod() {
		dailyPeriod++;
	}

	public long getMonthlyPeriod() {
		return monthlyPeriod;
	}

	public void incrementMonthlyPeriod() {
		monthlyPeriod++;
	}

	public long getAnnuallyPariod() {
		return annuallyPariod;
	}

	public void incrementAnuallyPeriod() {
		annuallyPariod++;
	}

	public long getEmptyPeriod() {
		return emptyPeriod;
	}

	public void incrementEmptyPeriod() {
		emptyPeriod++;
	}

	@Override
	public String toString() {
		return "PricingData [zeroModels=" + zeroModels + ", oneModel=" + oneModel + ", twoModels=" + twoModels
				+ ", threeModels=" + threeModels + ", fourModels=" + fourModels + ", freeModel=" + freeModel
				+ ", fixedModel=" + fixedModel + ", meteredModel=" + meteredModel + ", hybridModel=" + hybridModel
				+ ", emptyModel=" + emptyModel + ", dailyPeriod=" + dailyPeriod + ", monthlyPeriod=" + monthlyPeriod
				+ ", annuallyPariod=" + annuallyPariod + ", emptyPeriod=" + emptyPeriod + "]";
	}

}
