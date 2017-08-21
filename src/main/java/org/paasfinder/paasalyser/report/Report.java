package org.paasfinder.paasalyser.report;

import org.paasfinder.paasalyser.report.models.BusinessInfo;
import org.paasfinder.paasalyser.report.models.EconomicInfo;
import org.paasfinder.paasalyser.report.models.MetaInfo;
import org.paasfinder.paasalyser.statistics.Statistics;

public class Report {

	private MetaInfo metaInfo;
	private BusinessInfo businessInfo;
	private EconomicInfo economicInfo;

	public Report(Statistics statistics) {
		metaInfo = new MetaInfo(statistics.getProfilesCount(), statistics.getEolProfilesCount(),
				statistics.getInvalidProfilesCount(), statistics.getRevision(), statistics.getType());
		businessInfo = new BusinessInfo(statistics.getStatus(), statistics.getPricing());
		economicInfo = new EconomicInfo(statistics.getHosting(), statistics.getScaling(), statistics.getRuntimes(),
				statistics.getMiddleware(), statistics.getFrameworks(), statistics.getServices(),
				statistics.getExtensible(), statistics.getInfrastructures());
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
