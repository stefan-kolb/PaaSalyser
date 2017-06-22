package statistics;

import java.util.Map;

public interface Statistics {

	public Map<String, Double> evalRevision(Map<String, Long> data);

	public Map<String, Long> evalStatus(Map<String, Long> data);

	public Map<String, Double> evalStatusSince(Map<String, Long> data);

	public Map<String, String> evalType(Map<String, Long> data);

	public Map<String, Double> evalQos(Map<String, Double> data);
	
	public Map<String, Double> evalOverallCompliance(Map<String, Long> data);
	
	public Map<String, String> evalSpecificCompliance(Map<String, Long> data);

	public Map<String, String> evalPlatform(Map<String, Long> data);

	public Map<String, String> evalHosting(Map<String, Long> data);

	public Map<String, String> evalPricing(Map<String, Long> data);

	public Map<String, String> evalScaling(Map<String, Long> data);

	public Map<String, String> evalRuntimes(Map<String, Long> data);
	
	public Map<String, String> evalMiddleware(Map<String, Long> data);

	public Map<String, String> evalFrameworks(Map<String, Long> data);

	public Map<String, String> evalServices(Map<String, Long> data);

	public Map<String, String> evalExtensible(Map<String, Long> data);

	public Map<String, String> evalInfrastructures(Map<String, Long> data);

}
