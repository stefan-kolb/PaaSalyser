package report.models.supermodels;

import java.util.ArrayList;
import java.util.List;

import report.models.ExtensibleReport;
import report.models.FrameworksReport;
import report.models.HostingReport;
import report.models.InfrastructuresReport;
import report.models.MiddlewareReport;
import report.models.OverallComplianceReport;
import report.models.PlatformReport;
import report.models.PricingReport;
import report.models.QosReport;
import report.models.RevisionReport;
import report.models.RuntimesReport;
import report.models.ScalingReport;
import report.models.ServicesReport;
import report.models.SpecificComplianceReport;
import report.models.StatusReport;
import report.models.StatusSinceReport;
import report.models.TypeReport;

public class Report {

	private List<Object> metaInfo = new ArrayList<>();
	private List<Object> bizInfo = new ArrayList<>();
	private List<Object> ecoInfo = new ArrayList<>();

	public Report(RevisionReport revisionReport, StatusReport statusReport, StatusSinceReport statusSinceReport,
			TypeReport typeReport, QosReport qosReport, OverallComplianceReport overallComplianceReport,
			SpecificComplianceReport specificComplianceReport, PlatformReport platformReport,
			HostingReport hostingReport, PricingReport pricingReport, ScalingReport scalingReport,
			RuntimesReport runtimesReport, MiddlewareReport middlewareReport, FrameworksReport frameworksReport,
			ServicesReport servicesReport, ExtensibleReport extensibleReport,
			InfrastructuresReport infrastructuresReport) {
		super();

		metaInfo.add(revisionReport);
		metaInfo.add(typeReport);

		bizInfo.add(statusReport);
		bizInfo.add(statusSinceReport);
		bizInfo.add(pricingReport);
		bizInfo.add(qosReport);
		bizInfo.add(overallComplianceReport);
		bizInfo.add(specificComplianceReport);

		ecoInfo.add(scalingReport);
		ecoInfo.add(hostingReport);
		ecoInfo.add(extensibleReport);
		ecoInfo.add(frameworksReport);
		ecoInfo.add(middlewareReport);
		ecoInfo.add(runtimesReport);
		ecoInfo.add(servicesReport);
		ecoInfo.add(infrastructuresReport);
	}

	public List<Object> getMetaInfo() {
		return metaInfo;
	}

	public List<Object> getBizInfo() {
		return bizInfo;
	}

	public List<Object> getEcoInfo() {
		return ecoInfo;
	}

}
