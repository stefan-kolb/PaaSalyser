package statistics;

import java.util.Map;

public interface DataPreprocessing {

	/**
	 * Evaluates the age of Revision of a PaasProfile in Days
	 * 
	 * @param profiles
	 * @return A Map with keys: latest, oldest, mean and their value in Days.
	 */
	public Map<String, Long> evalRevision();

	public Map<String, Long> evalStatus();

	/**
	 * Evaluates the age of StatusSince of a PaasProfile in Days
	 * 
	 * @param profiles
	 * @return A Map with keys: latest, oldest, mean and their value in Days.
	 */
	public Map<String, Long> evalStatusSince();

	public Map<String, Long> evalType();

	/**
	 * Evaluates the Qos of the profiles and puts all profiles that have
	 * compliances into a list.
	 * 
	 * @param profiles
	 * @return
	 */
	public Map<String, Double> evalQos();

	public Map<String, Long> evalCompliance();

	public Map<String, Long> evalPlatform();

	public Map<String, Long> evalHosting();

	public Map<String, Long> evalPricing();

	public Map<String, Long> evalScaling();

	/**
	 * Evaluates the Runtime of a PaasProfile. A Profile might have multiple
	 * languages and a language might have multiple versions. For every Version
	 * of a language there is a key in the Map returned with the frequency as
	 * its value. The DataFormat of the key is ("language"|"version"). A version
	 * consists either of numbers of wildcards ("*").
	 * 
	 * @param profiles
	 * @return Map with language and version as a key and its frequency as value
	 */
	public Map<String, Long> evalRuntimes();

	public Map<String, Long> evalMiddleware();

	public Map<String, Long> evalFrameworks();

	public Map<String, Long> evalServices();

	public Map<String, Long> evalExtensible();

	public Map<String, Long> evalInfrastructures();

}
