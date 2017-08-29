package org.paasfinder.paasalyser.statistics.timeseries;

import java.util.ArrayList;
import java.util.List;

import org.paasfinder.paasalyser.report.PaasReport;
import org.paasfinder.paasalyser.report.models.MetaInfo;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeseriesStatistics {

	private final Logger logger = LoggerFactory.getLogger(TimeseriesStatistics.class);

	public TimeseriesStatistics() {
		super();
		logger.info("Initialising TimeseriesStatistics");
	}

	public List<String[]> evalProfileAmounts(List<MetaInfo> profiles) {
		List<String[]> data = new ArrayList<>(profiles.size());
		String[] profileData = new String[3];

		// Header Row
		profileData[0] = "Date";
		profileData[1] = "Active";
		profileData[2] = "EOL";
		data.add(profileData);

		System.out.println(profiles.size());
		for (MetaInfo profile : profiles) {
			profileData = new String[3];
			profileData[0] = profile.getDate().toString();
			profileData[1] = "" + profile.getNumberOfProfiles();
			profileData[2] = "" + profile.getNumberOfEolProfiles();
			data.add(profileData);
		}
		return data;
	}

	public List<String[]> evalRevision(List<MetaInfo> profiles) {
		List<String[]> data = new ArrayList<>(profiles.size());
		String[] profileData = new String[5];

		// Header Row
		profileData[0] = "Date";
		profileData[1] = "Mean";
		profileData[2] = "Median";
		profileData[3] = "Youngest";
		profileData[4] = "Oldest";
		data.add(profileData);

		// Table Rows
		for (MetaInfo profile : profiles) {
			profileData = new String[5];
			profileData[0] = profile.getDate().toString();
			profileData[1] = "" + profile.getRevisionReport().getMean();
			profileData[2] = "" + profile.getRevisionReport().getMedian();
			profileData[3] = "" + profile.getRevisionReport().getYoungestRevisions().get(0).getValue();
			profileData[4] = "" + profile.getRevisionReport().getOldestRevisions().get(0).getValue();
			data.add(profileData);
		}
		return data;
	}

	public List<String[]> evalType(List<MetaInfo> profiles) {
		List<String[]> data = new ArrayList<>(profiles.size());
		String[] profileData = new String[4];

		// Header Row
		profileData[0] = "Date";
		profileData[1] = "SaaS-Centric";
		profileData[2] = "Generic";
		profileData[3] = "IaaS-Centric";
		data.add(profileData);

		// Table Rows
		for (MetaInfo profile : profiles) {
			profileData = new String[4];
			profileData[0] = profile.getDate().toString();
			profileData[1] = "" + profile.getTypeReport().getSaasCentricPercent();
			profileData[2] = "" + profile.getTypeReport().getGenericPercent();
			profileData[3] = "" + profile.getTypeReport().getIaasCentricPercent();
			data.add(profileData);
		}
		return data;
	}

	public List<String[]> evalStatus(List<PaasReport> profiles) {
		List<String[]> data = new ArrayList<>(profiles.size());
		String[] profileData = new String[8];

		// Header Row
		profileData[0] = "Date";
		profileData[1] = "Alpha";
		profileData[2] = "Beta";
		profileData[3] = "Production";
		profileData[4] = "Mean StatusSince";
		profileData[5] = "Median StatusSince";
		profileData[6] = "Youngest StatusSince";
		profileData[7] = "Oldest StatusSince";
		data.add(profileData);

		// Table Rows
		for (PaasReport profile : profiles) {
			profileData = new String[8];
			profileData[0] = profile.getMetaInfo().getDate().toString();
			profileData[1] = "" + profile.getBusinessInfo().getStatusReport().getAlphaPercent();
			profileData[2] = "" + profile.getBusinessInfo().getStatusReport().getBetaPercent();
			profileData[3] = "" + profile.getBusinessInfo().getStatusReport().getProductionPercent();
			profileData[4] = "" + profile.getBusinessInfo().getStatusReport().getMeanStatusSince();
			profileData[5] = "" + profile.getBusinessInfo().getStatusReport().getMedianStatusSince();
			profileData[6] = ""
					+ profile.getBusinessInfo().getStatusReport().getYoungestStatusSince().get(0).getValue();
			profileData[7] = ""
					+ profile.getBusinessInfo().getStatusReport().getOldestStatusSince().get(0).getValue();
			data.add(profileData);
		}
		return data;
	}
	
	public List<String[]> evalPricing(List<PaasReport> profiles) {
		List<String[]> data = new ArrayList<>(profiles.size());
		String[] profileData = new String[8];

		// Header Row
		profileData[0] = "Date";
		profileData[1] = "Alpha";
		profileData[2] = "Beta";
		profileData[3] = "Production";
		profileData[4] = "Mean StatusSince";
		profileData[5] = "Median StatusSince";
		profileData[6] = "Youngest StatusSince";
		profileData[7] = "Oldest StatusSince";
		data.add(profileData);

		// Table Rows
		for (PaasReport profile : profiles) {
			profileData = new String[8];
			profileData[0] = profile.getMetaInfo().getDate().toString();
			profileData[1] = "" + profile.getBusinessInfo().getStatusReport().getAlphaPercent();
			profileData[2] = "" + profile.getBusinessInfo().getStatusReport().getBetaPercent();
			profileData[3] = "" + profile.getBusinessInfo().getStatusReport().getProductionPercent();
			profileData[4] = "" + profile.getBusinessInfo().getStatusReport().getMeanStatusSince();
			profileData[5] = "" + profile.getBusinessInfo().getStatusReport().getMedianStatusSince();
			profileData[6] = ""
					+ profile.getBusinessInfo().getStatusReport().getYoungestStatusSince().get(0).getValue();
			profileData[7] = ""
					+ profile.getBusinessInfo().getStatusReport().getOldestStatusSince().get(0).getValue();
			data.add(profileData);
		}
		return data;
	}

}
