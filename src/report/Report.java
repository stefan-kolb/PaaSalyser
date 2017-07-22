package report;

import report.models.BusinessInfo;
import report.models.EconomicInfo;
import report.models.MetaInfo;
import statistics.StatisticsImplWithModels;

public class Report {

	private MetaInfo metaInfo;
	private BusinessInfo businessInfo;
	private EconomicInfo economicInfo;

	public Report(StatisticsImplWithModels statistics) {
		metaInfo = new MetaInfo(statistics.getRevision(), statistics.getType());
		businessInfo = new BusinessInfo(statistics.getStatus(), statistics.getStatusSince(), statistics.getQos(),
				statistics.getOverallCompliance(), statistics.getSpecificCompliance(), statistics.getPricing());
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
