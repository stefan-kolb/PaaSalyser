package statistics;

import java.util.Map;

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

public interface StatisticsWithModels {
	
	public RevisionReport evalRevision(Map<String, Long> data);

	public StatusReport evalStatus(Map<String, Long> data);

	public StatusSinceReport evalStatusSince(Map<String, Long> data);

	public TypeReport evalType(Map<String, Long> data);

	public QosReport evalQos(Map<String, Double> data);
	
	public OverallComplianceReport evalOverallCompliance(Map<String, Long> data);
	
	public SpecificComplianceReport evalSpecificCompliance(Map<String, Long> data);

	public PlatformReport evalPlatform(Map<String, Long> data);

	public HostingReport evalHosting(Map<String, Long> data);

	public PricingReport evalPricing(Map<String, Long> data);

	public ScalingReport evalScaling(Map<String, Long> data);

	public RuntimesReport evalRuntimes(Map<String, Long> data);
	
	public MiddlewareReport evalMiddleware(Map<String, Long> data);

	public FrameworksReport evalFrameworks(Map<String, Long> data);

	public ServicesReport evalServices(Map<String, Long> data);

	public ExtensibleReport evalExtensible(Map<String, Long> data);

	public InfrastructuresReport evalInfrastructures(Map<String, Long> data);

}
