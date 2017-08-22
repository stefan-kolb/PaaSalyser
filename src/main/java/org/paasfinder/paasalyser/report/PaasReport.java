package org.paasfinder.paasalyser.report;

import java.time.LocalDate;

import org.paasfinder.paasalyser.report.models.BusinessInfo;
import org.paasfinder.paasalyser.report.models.EconomicInfo;
import org.paasfinder.paasalyser.report.models.MetaInfo;
import org.paasfinder.paasalyser.statistics.report.ReportStatistics;

public class PaasReport {

	private MetaInfo metaInfo;
	private BusinessInfo businessInfo;
	private EconomicInfo economicInfo;

	/**
	 * Generate a Report out of the statistics
	 * 
	 * @param statistics
	 *            Statistics to generate the report
	 * @throws IllegalStateException
	 *             If input parameter is null
	 */
	public PaasReport(LocalDate date, ReportStatistics statistics) throws IllegalStateException {
		if (statistics == null) {
			throw new IllegalStateException("Statistics was null");
		}
		metaInfo = new MetaInfo(date, statistics.getProfilesCount(), statistics.getEolProfilesCount(),
				statistics.getInvalidProfilesCount(), statistics.getRevision(), statistics.getType());
		businessInfo = new BusinessInfo(statistics.getStatus(), statistics.getPricing());
		economicInfo = new EconomicInfo(statistics.getHosting(), statistics.getScaling(), statistics.getRuntimes(),
				statistics.getMiddleware(), statistics.getFrameworks(), statistics.getServices(),
				statistics.getExtensible(), statistics.getInfrastructures());
	}

	/**
	 * Generate an empty report.
	 */
	public PaasReport() {
		super();
		@SuppressWarnings("unused")
		String errorMessage = "An error occurred during processing.";
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public BusinessInfo getBusinessInfo() {
		return businessInfo;
	}

	public EconomicInfo getEconomicInfo() {
		return economicInfo;
	}

}
