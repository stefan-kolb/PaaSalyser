package statistics;

import java.util.List;
import java.util.Map;

import models.PaasProfile;

public interface Statistics {

	public Map<String, Long> evalRevision(List<PaasProfile> profiles);

	public Map<String, Long> evalStatus(List<PaasProfile> profiles);

	public Map<String, Double> evalStatusSince(List<PaasProfile> profiles);

	public Map<String, Long> evalType(List<PaasProfile> profiles);

	public Map<String, Long> evalHosting(List<PaasProfile> profiles);

	public Map<String, Long> evalPricing(List<PaasProfile> profiles);

	public Map<String, Long> evalScaling(List<PaasProfile> profiles);

	public Map<String, Long> evalRuntimes(List<PaasProfile> profiles);
	
	public Map<String, Long> evalMiddleware(List<PaasProfile> profiles);
	
	public Map<String, Long> evalFrameworks(List<PaasProfile> profiles);
	
	public Map<String, Long> evalServices(List<PaasProfile> profiles);
	
	public Map<String, Long> evalExtensible(List<PaasProfile> profiles);
	
	public Map<String, Long> evalInfrastructures(List<PaasProfile> profiles);

}
