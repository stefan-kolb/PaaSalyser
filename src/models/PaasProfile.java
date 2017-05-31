package models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaasProfile {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("revision")
	@Expose
	private String revision;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("status_since")
	@Expose
	private String statusSince;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("hosting")
	@Expose
	private Hosting hosting;
	@SerializedName("pricings")
	@Expose
	private List<Pricing> pricings = null;
	@SerializedName("scaling")
	@Expose
	private Scaling scaling;
	@SerializedName("runtimes")
	@Expose
	private List<Runtime> runtimes = null;
	@SerializedName("middlewares")
	@Expose
	private List<Middleware> middlewares = null;
	@SerializedName("frameworks")
	@Expose
	private List<Framework> frameworks = null;
	@SerializedName("services")
	@Expose
	private Services services;
	@SerializedName("extensible")
	@Expose
	private Boolean extensible;
	@SerializedName("infrastructures")
	@Expose
	private List<Object> infrastructures = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusSince() {
		return statusSince;
	}

	public void setStatusSince(String statusSince) {
		this.statusSince = statusSince;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Hosting getHosting() {
		return hosting;
	}

	public void setHosting(Hosting hosting) {
		this.hosting = hosting;
	}

	public List<Pricing> getPricings() {
		return pricings;
	}

	public void setPricings(List<Pricing> pricings) {
		this.pricings = pricings;
	}

	public Scaling getScaling() {
		return scaling;
	}

	public void setScaling(Scaling scaling) {
		this.scaling = scaling;
	}

	public List<Runtime> getRuntimes() {
		return runtimes;
	}

	public void setRuntimes(List<Runtime> runtimes) {
		this.runtimes = runtimes;
	}

	public List<Middleware> getMiddlewares() {
		return middlewares;
	}

	public void setMiddlewares(List<Middleware> middlewares) {
		this.middlewares = middlewares;
	}

	public List<Framework> getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(List<Framework> frameworks) {
		this.frameworks = frameworks;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public Boolean getExtensible() {
		return extensible;
	}

	public void setExtensible(Boolean extensible) {
		this.extensible = extensible;
	}

	public List<Object> getInfrastructures() {
		return infrastructures;
	}

	public void setInfrastructures(List<Object> infrastructures) {
		this.infrastructures = infrastructures;
	}

}
