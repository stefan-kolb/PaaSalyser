package org.paasfinder.paasalyser.statistics.timeseries;

import java.util.ArrayList;
import java.util.List;

import org.paasfinder.paasalyser.report.PaasReport;
import org.paasfinder.paasalyser.report.models.MetaInfo;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultDouble;
import org.paasfinder.paasalyser.statistics.report.models.SimpleResultLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeseriesStatistics {

	private final Logger logger = LoggerFactory.getLogger(TimeseriesStatistics.class);

	public TimeseriesStatistics() {
		super();
		logger.info("Initialising TimeseriesStatistics");
	}

	public List<String[]> evalProfileAmounts(List<MetaInfo> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportData = new String[3];

		// Header Row
		reportData[0] = "Date";
		reportData[1] = "Active";
		reportData[2] = "EOL";
		data.add(reportData);

		for (MetaInfo report : reports) {
			reportData = new String[3];
			reportData[0] = report.getDate().toString();

			reportData[1] = String.valueOf(report.getNumberOfProfiles());
			reportData[2] = String.valueOf(report.getNumberOfEolProfiles());

			data.add(reportData);
		}
		return data;
	}

	public List<String[]> evalRevision(List<MetaInfo> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportData = new String[13];

		// Header Row
		reportData[0] = "Date";

		reportData[1] = "Mean";
		reportData[2] = "Median";

		reportData[3] = "Youngest";
		reportData[4] = "Second youngest";
		reportData[5] = "Third youngest";
		reportData[6] = "Forth youngest";
		reportData[7] = "Fifth youngest";

		reportData[8] = "Oldest";
		reportData[9] = "Second oldest";
		reportData[10] = "Third oldest";
		reportData[11] = "Forth oldest";
		reportData[12] = "Fifth oldest";

		data.add(reportData);

		// Table Rows
		for (MetaInfo report : reports) {
			reportData = new String[13];
			reportData[0] = report.getDate().toString();

			reportData[1] = Double.toString(report.getRevisionReport().getMean()).replace('.', ',');
			reportData[2] = Double.toString(report.getRevisionReport().getMedian()).replace('.', ',');

			reportData[3] = String.valueOf(report.getRevisionReport().getYoungestRevisions().get(0).getValue());
			reportData[4] = String.valueOf(report.getRevisionReport().getYoungestRevisions().get(1).getValue());
			reportData[5] = String.valueOf(report.getRevisionReport().getYoungestRevisions().get(2).getValue());
			reportData[6] = String.valueOf(report.getRevisionReport().getYoungestRevisions().get(3).getValue());
			reportData[7] = String.valueOf(report.getRevisionReport().getYoungestRevisions().get(4).getValue());

			reportData[8] = String.valueOf(report.getRevisionReport().getOldestRevisions().get(0).getValue());
			reportData[9] = String.valueOf(report.getRevisionReport().getOldestRevisions().get(1).getValue());
			reportData[10] = String.valueOf(report.getRevisionReport().getOldestRevisions().get(2).getValue());
			reportData[11] = String.valueOf(report.getRevisionReport().getOldestRevisions().get(3).getValue());
			reportData[12] = String.valueOf(report.getRevisionReport().getOldestRevisions().get(4).getValue());

			data.add(reportData);
		}
		return data;
	}

	public List<String[]> evalType(List<MetaInfo> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportData = new String[4];

		// Header Row
		reportData[0] = "Date";

		reportData[1] = "SaaS-Centric";
		reportData[2] = "Generic";
		reportData[3] = "IaaS-Centric";
		data.add(reportData);

		// Table Rows
		for (MetaInfo report : reports) {
			reportData = new String[4];
			reportData[0] = report.getDate().toString();

			reportData[1] = String.valueOf(report.getTypeReport().getSaasCentricPercent()).replace('.', ',');
			reportData[2] = String.valueOf(report.getTypeReport().getGenericPercent()).replace('.', ',');
			reportData[3] = String.valueOf(report.getTypeReport().getIaasCentricPercent()).replace('.', ',');

			data.add(reportData);
		}
		return data;
	}

	public List<String[]> evalPlatform(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportData = new String[2];

		// Header Row
		reportData[0] = "Date";

		reportData[1] = "Profiles with platform";

		// Table Rows
		for (PaasReport report : reports) {
			reportData = new String[2];
			reportData[0] = report.getMetaInfo().getDate().toString();

			reportData[1] = String.valueOf(report.getEconomicInfo().getPlatformReport().getPlatformProfilesPercent())
					.replace('.', ',');

			data.add(reportData);
		}
		return data;
	}

	public List<String[]> evalStatus(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportData = new String[4];

		// Header Row
		reportData[0] = "Date";

		reportData[1] = "Alpha";
		reportData[2] = "Beta";
		reportData[3] = "Production";
//
//		reportData[4] = "Mean StatusSince";
//		reportData[5] = "Median StatusSince";
//
//		reportData[6] = "Youngest StatusSince";
//		reportData[7] = "Second youngest StatusSince";
//		reportData[8] = "Third youngest StatusSince";
//
//		reportData[9] = "Oldest StatusSince";
//		reportData[10] = "Second oldest StatusSince";
//		reportData[11] = "Third oldest StatusSince";
		data.add(reportData);

		// Table Rows
		for (PaasReport report : reports) {
			reportData = new String[4];
			reportData[0] = report.getMetaInfo().getDate().toString();

			reportData[1] = String.valueOf(report.getBusinessInfo().getStatusReport().getAlphaPercent()).replace('.',
					',');
			reportData[2] = String.valueOf(report.getBusinessInfo().getStatusReport().getBetaPercent()).replace('.',
					',');
			reportData[3] = String.valueOf(report.getBusinessInfo().getStatusReport().getProductionPercent())
					.replace('.', ',');

			// reportData[4] =
			// String.valueOf(report.getBusinessInfo().getStatusReport().getMeanStatusSince()).replace('.',
			// ',');
			// reportData[5] =
			// String.valueOf(report.getBusinessInfo().getStatusReport().getMedianStatusSince())
			// .replace('.', ',');
			//
			// reportData[6] = String
			// .valueOf(report.getBusinessInfo().getStatusReport().getYoungestStatusSince().get(0).getValue());
			// reportData[7] = String
			// .valueOf(report.getBusinessInfo().getStatusReport().getYoungestStatusSince().get(1).getValue());
			// reportData[8] = String
			// .valueOf(report.getBusinessInfo().getStatusReport().getYoungestStatusSince().get(2).getValue());
			//
			// reportData[9] = String
			// .valueOf(report.getBusinessInfo().getStatusReport().getOldestStatusSince().get(0).getValue());
			// reportData[10] = String
			// .valueOf(report.getBusinessInfo().getStatusReport().getOldestStatusSince().get(1).getValue());
			// reportData[11] = String
			// .valueOf(report.getBusinessInfo().getStatusReport().getOldestStatusSince().get(2).getValue());

			data.add(reportData);
		}
		return data;
	}

	public List<String[]> evalPricing(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[13];

		// Header Row
		reportsData[0] = "Date";

		reportsData[1] = "Zero pricings per profile";
		reportsData[2] = "One pricing per profile";
		reportsData[3] = "Two pricings per profile";
		reportsData[4] = "Three pricings per profile";
		reportsData[5] = "Four pricings per profile";

		reportsData[6] = "Free";
		reportsData[7] = "Fixed";
		reportsData[8] = "Metered";
		reportsData[9] = "Hybrid";

		reportsData[10] = "Daily";
		reportsData[11] = "Monthly";
		reportsData[12] = "Anually";

		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			long zero = 0, one = 0, two = 0, three = 0, four = 0;
			long free = 0, fixed = 0, metered = 0, hybrid = 0;
			long daily = 0, monthly = 0, anually = 0;

			for (SimpleResultLong elem : report.getBusinessInfo().getPricingReport().getNumberOfModelsPerProfile()) {
				if (elem.getKey().equals("Zero Models")) {
					zero = elem.getValue();
				} else if (elem.getKey().equals("One Models")) {
					one = elem.getValue();
				} else if (elem.getKey().equals("Two Models")) {
					two = elem.getValue();
				} else if (elem.getKey().equals("Three Models")) {
					three = elem.getValue();
				} else if (elem.getKey().equals("Four Models")) {
					four = elem.getValue();
				}
			}

			for (SimpleResultLong elem : report.getBusinessInfo().getPricingReport().getModels()) {
				if (elem.getKey().equals("Free")) {
					free = elem.getValue();
				} else if (elem.getKey().equals("Fixed")) {
					fixed = elem.getValue();
				} else if (elem.getKey().equals("Metered")) {
					metered = elem.getValue();
				} else if (elem.getKey().equals("Hybrid")) {
					hybrid = elem.getValue();
				}
			}

			for (SimpleResultLong elem : report.getBusinessInfo().getPricingReport().getPeriods()) {
				if (elem.getKey().equals("Daily")) {
					daily = elem.getValue();
				} else if (elem.getKey().equals("Monthly")) {
					monthly = elem.getValue();
				} else if (elem.getKey().equals("Anually")) {
					anually = elem.getValue();
				}
			}

			reportsData = new String[13];

			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String.valueOf(zero);
			reportsData[2] = String.valueOf(one);
			reportsData[3] = String.valueOf(two);
			reportsData[4] = String.valueOf(three);
			reportsData[5] = String.valueOf(four);

			reportsData[6] = String.valueOf(free);
			reportsData[7] = String.valueOf(fixed);
			reportsData[8] = String.valueOf(metered);
			reportsData[9] = String.valueOf(hybrid);

			reportsData[10] = String.valueOf(daily);
			reportsData[11] = String.valueOf(monthly);
			reportsData[12] = String.valueOf(anually);

			data.add(reportsData);
		}
		return data;
	}

	public List<String[]> evalHosting(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[5];

		// Header Row
		reportsData[0] = "Date";
		reportsData[1] = "Private";
		reportsData[2] = "Public";
		reportsData[3] = "Hybrid";
		reportsData[4] = "Virtual Private";
		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			reportsData = new String[5];
			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String.valueOf(report.getEconomicInfo().getHostingReport().getPrivateHostingPercent())
					.replace('.', ',');
			reportsData[2] = String.valueOf(report.getEconomicInfo().getHostingReport().getPublicHostingPercent())
					.replace('.', ',');
			reportsData[3] = String.valueOf(report.getEconomicInfo().getHostingReport().getHybridHostingPercent())
					.replace('.', ',');
			reportsData[4] = String
					.valueOf(report.getEconomicInfo().getHostingReport().getVirtualPrivateHostingPercent())
					.replace('.', ',');

			data.add(reportsData);
		}
		return data;
	}

	public List<String[]> evalScaling(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[4];

		// Header Row
		reportsData[0] = "Date";
		reportsData[1] = "Vertical";
		reportsData[2] = "Horizontal";
		reportsData[3] = "Auto";
		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			reportsData = new String[4];
			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String.valueOf(report.getEconomicInfo().getScalingReport().getVerticalPercent())
					.replace('.', ',');
			reportsData[2] = String.valueOf(report.getEconomicInfo().getScalingReport().getHorizontalPercent())
					.replace('.', ',');
			reportsData[3] = String.valueOf(report.getEconomicInfo().getScalingReport().getAutoPercent()).replace('.',
					',');

			data.add(reportsData);
		}
		return data;
	}

	public List<String[]> evalRuntimesProfiles(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[10];

		// Header Row
		reportsData[0] = "Date";

		reportsData[1] = "Language-specific";
		reportsData[2] = "Polyglot";

		reportsData[3] = "Mean runtimes per profile";
		reportsData[4] = "Median runtimes per profile";

		reportsData[5] = "Most runtimes per profile";
		reportsData[6] = "Second most runtimes per profile";
		reportsData[7] = "Third most runtimes per profile";
		reportsData[8] = "Fourth most runtimes per profile";
		reportsData[9] = "Fifth most runtimes per profile";

		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			reportsData = new String[10];

			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String.valueOf(report.getEconomicInfo().getRuntimesReport().getLanguageSpecific());
			reportsData[2] = String.valueOf(report.getEconomicInfo().getRuntimesReport().getPolyglot());

			reportsData[3] = String.valueOf(report.getEconomicInfo().getRuntimesReport().getMeanNumberOfRuntimes())
					.replace('.', ',');
			reportsData[4] = String.valueOf(report.getEconomicInfo().getRuntimesReport().getMedianNumberOfRuntimes())
					.replace('.', ',');

			reportsData[5] = String
					.valueOf(report.getEconomicInfo().getRuntimesReport().getTopNumberOfRuntimes().get(0).getValue());
			reportsData[6] = String
					.valueOf(report.getEconomicInfo().getRuntimesReport().getTopNumberOfRuntimes().get(1).getValue());
			reportsData[7] = String
					.valueOf(report.getEconomicInfo().getRuntimesReport().getTopNumberOfRuntimes().get(2).getValue());
			reportsData[8] = String
					.valueOf(report.getEconomicInfo().getRuntimesReport().getTopNumberOfRuntimes().get(3).getValue());
			reportsData[9] = String
					.valueOf(report.getEconomicInfo().getRuntimesReport().getTopNumberOfRuntimes().get(4).getValue());


			data.add(reportsData);
		}
		return data;
	}

	public List<String[]> evalRuntimesShare(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[17];

		// Header Row
		reportsData[0] = "Date";

		reportsData[1] = "Apex";
		reportsData[2] = "Clojure";
		reportsData[3] = "Cobol";
		reportsData[4] = "Dotnet";
		reportsData[5] = "Erlang";
		reportsData[6] = "Go";
		reportsData[7] = "Groovy";
		reportsData[8] = "Haskell";
		reportsData[9] = "Java";
		reportsData[10] = "Lua";
		reportsData[11] = "Node";
		reportsData[12] = "Perl";
		reportsData[13] = "Php";
		reportsData[14] = "Python";
		reportsData[15] = "Ruby";
		reportsData[16] = "Scala";

		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			double apex = 0, clojure = 0, cobol = 0, dotnet = 0, erlang = 0, go = 0, groovy = 0, haskell = 0, java = 0,
					lua = 0, node = 0, perl = 0, php = 0, python = 0, ruby = 0, scala = 0;

			for (SimpleResultDouble elem : report.getEconomicInfo().getRuntimesReport().getRuntimesShare()) {
				if (elem.getKey().equals("apex")) {
					apex = elem.getValue();
				} else if (elem.getKey().equals("clojure")) {
					clojure = elem.getValue();
				} else if (elem.getKey().equals("cobol")) {
					cobol = elem.getValue();
				} else if (elem.getKey().equals("dotnet")) {
					apex = elem.getValue();
				} else if (elem.getKey().equals("erlang")) {
					erlang = elem.getValue();
				} else if (elem.getKey().equals("go")) {
					go = elem.getValue();
				} else if (elem.getKey().equals("groovy")) {
					groovy = elem.getValue();
				} else if (elem.getKey().equals("haskell")) {
					haskell = elem.getValue();
				} else if (elem.getKey().equals("java")) {
					java = elem.getValue();
				} else if (elem.getKey().equals("lua")) {
					lua = elem.getValue();
				} else if (elem.getKey().equals("node")) {
					node = elem.getValue();
				} else if (elem.getKey().equals("perl")) {
					perl = elem.getValue();
				} else if (elem.getKey().equals("php")) {
					php = elem.getValue();
				} else if (elem.getKey().equals("python")) {
					python = elem.getValue();
				} else if (elem.getKey().equals("ruby")) {
					ruby = elem.getValue();
				} else if (elem.getKey().equals("scala")) {
					scala = elem.getValue();
				}
			}

			reportsData = new String[17];

			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String.valueOf(apex).replace('.', ',');
			reportsData[2] = String.valueOf(clojure).replace('.', ',');
			reportsData[3] = String.valueOf(cobol).replace('.', ',');
			reportsData[4] = String.valueOf(dotnet).replace('.', ',');
			reportsData[5] = String.valueOf(erlang).replace('.', ',');
			reportsData[6] = String.valueOf(go).replace('.', ',');
			reportsData[7] = String.valueOf(groovy).replace('.', ',');
			reportsData[8] = String.valueOf(haskell).replace('.', ',');
			reportsData[9] = String.valueOf(java).replace('.', ',');
			reportsData[10] = String.valueOf(lua).replace('.', ',');
			reportsData[11] = String.valueOf(node).replace('.', ',');
			reportsData[12] = String.valueOf(perl).replace('.', ',');
			reportsData[13] = String.valueOf(php).replace('.', ',');
			reportsData[14] = String.valueOf(python).replace('.', ',');
			reportsData[15] = String.valueOf(ruby).replace('.', ',');
			reportsData[16] = String.valueOf(scala).replace('.', ',');

			data.add(reportsData);
		}
		return data;
	}

	public List<String[]> evalServices(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[2];

		// Header Row
		reportsData[0] = "Date";

		reportsData[1] = "Profiles with Native Services in percent";

		// reportsData[2] = "Top profile native service";
		// reportsData[3] = "Second profile native service";
		// reportsData[4] = "Third profile native service";
		// reportsData[5] = "Fourth profile native service";
		// reportsData[6] = "Fifth profile native service";
		//
		// reportsData[2] = "Top native service";
		// reportsData[3] = "Second native service";
		// reportsData[4] = "Third native service";
		// reportsData[5] = "Fourth native service";
		// reportsData[6] = "Fifth native service";
		//
		// reportsData[2] = "Top type native service";
		// reportsData[3] = "Second type native service";
		// reportsData[4] = "Third type native service";
		// reportsData[5] = "Fourth type native service";
		// reportsData[6] = "Fifth type native service";

		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			reportsData = new String[2];

			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String
					.valueOf(report.getEconomicInfo().getServicesReport().getNativeServicesProfilesPercent())
					.replace('.', ',');

			// reportsData[2] =
			// String.valueOf(report.getEconomicInfo().getServicesReport().get);
			// reportsData[3] = String.valueOf(cobol);
			// reportsData[4] = String.valueOf(dotnet);
			// reportsData[5] = String.valueOf(erlang);
			// reportsData[6] = String.valueOf(go);
			// reportsData[7] = String.valueOf(groovy);
			// reportsData[8] = String.valueOf(haskell);
			// reportsData[9] = String.valueOf(java);
			// reportsData[10] = String.valueOf(lua);
			// reportsData[11] = String.valueOf(node);
			// reportsData[12] = String.valueOf(perl);
			// reportsData[13] = String.valueOf(php);
			// reportsData[14] = String.valueOf(python);
			// reportsData[15] = String.valueOf(ruby);
			// reportsData[16] = String.valueOf(scala);
			// reportsData[17] = String.valueOf(others);

			data.add(reportsData);
		}
		return data;
	}

	public List<String[]> evalExtensible(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[2];

		// Header Row
		reportsData[0] = "Date";

		reportsData[1] = "Extensible profiles in percent";

		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			reportsData = new String[2];

			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String
					.valueOf(report.getEconomicInfo().getExtensibleReport().getExtensibleProfilesPercent())
					.replace('.', ',');

			data.add(reportsData);
		}
		return data;
	}

	public List<String[]> evalInfraStructure(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[8];

		// Header Row
		reportsData[0] = "Date";

		reportsData[1] = "Mean infrastructure per profile";
		reportsData[2] = "Median infrastructure per profile";

		reportsData[3] = "Top infrastructure per profile";
		reportsData[4] = "Second infrastructure per profile";
		reportsData[5] = "Third infrastructure per profile";
		reportsData[6] = "Forth infrastructure per profile";
		reportsData[7] = "Fifth infrastructure per profile";

		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			reportsData = new String[8];

			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String
					.valueOf(report.getEconomicInfo().getInfrastructuresReport().getMeanInfrastrcturesPerProfile())
					.replace('.', ',');
			reportsData[2] = String
					.valueOf(report.getEconomicInfo().getInfrastructuresReport().getMedianInfrastrcturesPerProfile())
					.replace('.', ',');

			reportsData[3] = String.valueOf(report.getEconomicInfo().getInfrastructuresReport()
					.getTopInfrastrcturesPerProfile().get(0).getValue());
			reportsData[4] = String.valueOf(report.getEconomicInfo().getInfrastructuresReport()
					.getTopInfrastrcturesPerProfile().get(1).getValue());
			reportsData[5] = String.valueOf(report.getEconomicInfo().getInfrastructuresReport()
					.getTopInfrastrcturesPerProfile().get(2).getValue());
			reportsData[6] = String.valueOf(report.getEconomicInfo().getInfrastructuresReport()
					.getTopInfrastrcturesPerProfile().get(3).getValue());
			reportsData[7] = String.valueOf(report.getEconomicInfo().getInfrastructuresReport()
					.getTopInfrastrcturesPerProfile().get(4).getValue());

			data.add(reportsData);
		}
		return data;
	}
	
	public List<String[]> evalInfraStructureContinents(List<PaasReport> reports) {
		List<String[]> data = new ArrayList<>(reports.size());
		String[] reportsData = new String[7];

		// Header Row
		reportsData[0] = "Date";

		reportsData[1] = "North America";
		reportsData[2] = "Europe";
		reportsData[3] = "Asia";
		reportsData[4] = "Oceania";
		reportsData[5] = "South America";
		reportsData[6] = "Africa";

		data.add(reportsData);

		// Table Rows
		for (PaasReport report : reports) {
			double na = 0, eu = 0, as = 0, oc = 0, sa = 0, af = 0;

			for (SimpleResultDouble elem : report.getEconomicInfo().getInfrastructuresReport()
					.getPercentOfProfilesPerContinent()) {
				if (elem.getKey().equals("NA")) {
					na = elem.getValue();
				} else if (elem.getKey().equals("EU")) {
					eu = elem.getValue();
				} else if (elem.getKey().equals("AS")) {
					as = elem.getValue();
				} else if (elem.getKey().equals("OC")) {
					oc = elem.getValue();
				} else if (elem.getKey().equals("SA")) {
					sa = elem.getValue();
				} else if (elem.getKey().equals("AF")) {
					af = elem.getValue();
				}
			}

			reportsData = new String[7];
			
			reportsData[0] = report.getMetaInfo().getDate().toString();

			reportsData[1] = String.valueOf(na).replace('.', ',');
			reportsData[2] = String.valueOf(eu).replace('.', ',');
			reportsData[3] = String.valueOf(as).replace('.', ',');
			reportsData[4] = String.valueOf(oc).replace('.', ',');
			reportsData[5] = String.valueOf(sa).replace('.', ',');
			reportsData[6] = String.valueOf(af).replace('.', ',');

			data.add(reportsData);
		}
		return data;
	}

}
