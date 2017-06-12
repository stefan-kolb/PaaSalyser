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

	public Map<String, Long> evalPlatform(Map<String, Long> data);

	public Map<String, Long> evalHosting(Map<String, Long> data);

	public Map<String, Double> evalPricing(Map<String, Long> data);

	public Map<String, Double> evalScaling(Map<String, Long> data);

	public Map<String, Double> evalRuntimes(Map<String, Long> data);

	public Map<String, Double> evalMiddleware(Map<String, Long> data);

	public Map<String, Double> evalFrameworks(Map<String, Long> data);

	public Map<String, Double> evalServices(Map<String, Long> data);

	public Map<String, Double> evalExtensible(Map<String, Long> data);

	public Map<String, Double> evalInfrastructures(Map<String, Long> data);

}
