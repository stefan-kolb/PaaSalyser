package statistics;

import java.util.Map;

public interface Statistics {

	public Map<String, String> evalRevision(Map<String, Double> preprocessedData);

	public Map<String, String> evalStatus(Map<String, Long> preprocessedData);

	public Map<String, String> evalStatusSince(Map<String, Double> preprocessedData);

	public Map<String, String> evalType(Map<String, Long> preprocessedData);

	public Map<String, String> evalQos(Map<String, Double> preprocessedData);

	public Map<String, String> evalPlatform(Map<String, Long> preprocessedData);

	public Map<String, String> evalHosting(Map<String, Long> preprocessedData);

	public Map<String, String> evalPricing(Map<String, Long> preprocessedData);

	public Map<String, String> evalScaling(Map<String, Long> preprocessedData);

	public Map<String, String> evalRuntimes(Map<String, Long> preprocessedData);

	public Map<String, String> evalMiddleware(Map<String, Long> preprocessedData);

	public Map<String, String> evalFrameworks(Map<String, Long> preprocessedData);

	public Map<String, String> evalServices(Map<String, Long> preprocessedData);

	public Map<String, String> evalExtensible(Map<String, Long> preprocessedData);

	public Map<String, String> evalInfrastructures(Map<String, Long> preprocessedData);

}
