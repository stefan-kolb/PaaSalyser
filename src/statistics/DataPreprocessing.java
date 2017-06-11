package statistics;

import java.util.List;
import java.util.Map;

import models.PaasProfile;

public interface DataPreprocessing {

	/**
	 * Evaluates the age of Revision of a PaasProfile in Days
	 * 
	 * @param profiles
	 * @return A Map with keys: latest, oldest, mean and their value in Days.
	 */
	public Map<String, Long> evalRevision(List<PaasProfile> profiles);

	public Map<String, Long> evalStatus(List<PaasProfile> profiles);

	/**
	 * Evaluates the age of StatusSince of a PaasProfile in Days
	 * 
	 * @param profiles
	 * @return A Map with keys: latest, oldest, mean and their value in Days.
	 */
	public Map<String, Long> evalStatusSince(List<PaasProfile> profiles);

	public Map<String, Long> evalType(List<PaasProfile> profiles);

	public Map<String, Double> evalQos(List<PaasProfile> profiles);

	public Map<String, Long> evalPlatform(List<PaasProfile> profiles);

	public Map<String, Long> evalHosting(List<PaasProfile> profiles);

	public Map<String, Long> evalPricing(List<PaasProfile> profiles);

	public Map<String, Long> evalScaling(List<PaasProfile> profiles);

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
	public Map<String, Long> evalRuntimes(List<PaasProfile> profiles);

	public Map<String, Long> evalMiddleware(List<PaasProfile> profiles);

	public Map<String, Long> evalFrameworks(List<PaasProfile> profiles);

	public Map<String, Long> evalServices(List<PaasProfile> profiles);

	public Map<String, Long> evalExtensible(List<PaasProfile> profiles);

	public Map<String, Long> evalInfrastructures(List<PaasProfile> profiles);

}
