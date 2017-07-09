package models;

import java.util.Map;

public class Report {

	private Map<String, String> revision;
	private Map<String, String> status;
	private Map<String, String> statusSince;
	private Map<String, String> type;
	private Map<String, String> qos;
	private Map<String, String> overallCompliance;
	private Map<String, String> specificCompliance;
	private Map<String, String> platform;
	private Map<String, String> hosting;
	private Map<String, String> pricing;
	private Map<String, String> scaling;
	private Map<String, String> runtimes;
	private Map<String, String> middleware;
	private Map<String, String> frameworks;
	private Map<String, String> services;
	private Map<String, String> extensible;
	private Map<String, String> infrastructures;

	public Report(Map<String, String> revision, Map<String, String> status, Map<String, String> statusSince,
			Map<String, String> type, Map<String, String> qos, Map<String, String> overallCompliance,
			Map<String, String> specificCompliance, Map<String, String> platform, Map<String, String> hosting,
			Map<String, String> pricing, Map<String, String> scaling, Map<String, String> runtimes,
			Map<String, String> middleware, Map<String, String> frameworks, Map<String, String> services,
			Map<String, String> extensible, Map<String, String> infrastructures) {
		super();
		this.revision = revision;
		this.status = status;
		this.statusSince = statusSince;
		this.type = type;
		this.qos = qos;
		this.overallCompliance = overallCompliance;
		this.specificCompliance = specificCompliance;
		this.platform = platform;
		this.hosting = hosting;
		this.pricing = pricing;
		this.scaling = scaling;
		this.runtimes = runtimes;
		this.middleware = middleware;
		this.frameworks = frameworks;
		this.services = services;
		this.extensible = extensible;
		this.infrastructures = infrastructures;
	}

	public Map<String, String> getRevision() {
		return revision;
	}

	public Map<String, String> getStatus() {
		return status;
	}

	public Map<String, String> getStatusSince() {
		return statusSince;
	}

	public Map<String, String> getType() {
		return type;
	}

	public Map<String, String> getQos() {
		return qos;
	}

	public Map<String, String> getOverallCompliance() {
		return overallCompliance;
	}

	public Map<String, String> getSpecificCompliance() {
		return specificCompliance;
	}

	public Map<String, String> getPlatform() {
		return platform;
	}

	public Map<String, String> getHosting() {
		return hosting;
	}

	public Map<String, String> getPricing() {
		return pricing;
	}

	public Map<String, String> getScaling() {
		return scaling;
	}

	public Map<String, String> getRuntimes() {
		return runtimes;
	}

	public Map<String, String> getMiddleware() {
		return middleware;
	}

	public Map<String, String> getFrameworks() {
		return frameworks;
	}

	public Map<String, String> getServices() {
		return services;
	}

	public Map<String, String> getExtensible() {
		return extensible;
	}

	public Map<String, String> getInfrastructures() {
		return infrastructures;
	}

}
