package statistics;

import java.util.List;
import java.util.Map;

import models.PaasProfile;

public interface Statistics {

	public Map<String, String> evalRevision(List<PaasProfile> profiles);

	public Map<String, String> evalStatus(List<PaasProfile> profiles);

	public Map<String, String> evalStatusSince(List<PaasProfile> profiles);

	public Map<String, String> evalType(List<PaasProfile> profiles);

	public Map<String, String> evalQos(List<PaasProfile> profiles);

	public Map<String, String> evalPlatform(List<PaasProfile> profiles);

	public Map<String, String> evalHosting(List<PaasProfile> profiles);

	public Map<String, String> evalPricing(List<PaasProfile> profiles);

	public Map<String, String> evalScaling(List<PaasProfile> profiles);

	public Map<String, String> evalRuntimes(List<PaasProfile> profiles);

	public Map<String, String> evalMiddleware(List<PaasProfile> profiles);

	public Map<String, String> evalFrameworks(List<PaasProfile> profiles);

	public Map<String, String> evalServices(List<PaasProfile> profiles);

	public Map<String, String> evalExtensible(List<PaasProfile> profiles);

	public Map<String, String> evalInfrastructures(List<PaasProfile> profiles);

}
