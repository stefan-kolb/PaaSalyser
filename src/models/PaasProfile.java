package models;

import java.util.LinkedList;
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
	@SerializedName("platform")
	@Expose
	private String platform;
	@SerializedName("hosting")
	@Expose
	private Hosting hosting;
	@SerializedName("pricings")
	@Expose
	private List<Pricing> pricings = null;
	@SerializedName("qos")
	@Expose
	private Qos qos;
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
	private String extensible;
	@SerializedName("infrastructures")
	@Expose
	private List<Infrastructure> infrastructures = null;

	boolean failed = false;

	public PaasProfile(String name, String revision, String url, String status, String statusSince, String type,
			Hosting hosting, List<Pricing> pricings, Scaling scaling, List<Runtime> runtimes,
			List<Middleware> middlewares, List<Framework> frameworks, Services services, String extensible,
			List<Infrastructure> infrastructures) {
		super();
		this.name = name;
		this.revision = revision;
		this.url = url;
		this.status = status;
		this.statusSince = statusSince;
		this.type = type;
		this.hosting = hosting;
		this.pricings = pricings;
		this.scaling = scaling;
		this.runtimes = runtimes;
		this.middlewares = middlewares;
		this.frameworks = frameworks;
		this.services = services;
		this.extensible = extensible;
		this.infrastructures = infrastructures;
	}

	/**
	 * This Constructor is used if an error occured during scanning a JSON File
	 * 
	 * @param failed
	 *            true if scan failed
	 */
	public PaasProfile(boolean errorOccurred) {
		if (errorOccurred == true)
			this.failed = true;
	}

	public boolean isFailed() {
		return failed;
	}

	public String getName() {
		if (this.name == null) {
			return "null";
		} else {
			return name;
		}
	}

	public String getRevision() {
		if (this.revision == null) {
			return "null";
		} else {
			return revision;
		}
	}

	public String getUrl() {
		if (this.url == null) {
			return "null";
		} else {
			return url;
		}
	}

	public String getStatus() {
		if (this.status == null) {
			return "null";
		} else {
			return status;
		}
	}

	public String getStatusSince() {
		if (this.statusSince == null) {
			return "null";
		} else {
			return statusSince;
		}
	}

	public String getPlatform() {
		if (this.platform == null) {
			return "null";
		} else {
			return platform;
		}
	}

	public Qos getQos() {
		if (this.qos == null) {
			return new Qos();
		} else {
			return qos;
		}
	}

	public String getType() {
		if (this.type == null) {
			return "null";
		} else {
			return type;
		}
	}

	public Hosting getHosting() {
		if (this.hosting == null) {
			return new Hosting();
		} else {
			return hosting;
		}
	}

	public List<Pricing> getPricings() {
		if (this.pricings == null) {
			return new LinkedList<Pricing>();
		} else {
			return pricings;
		}
	}

	public Scaling getScaling() {
		if (this.scaling == null) {
			return new Scaling();
		} else {
			return scaling;
		}
	}

	public List<Runtime> getRuntimes() {
		if (this.runtimes == null) {
			return new LinkedList<Runtime>();
		} else {
			return runtimes;
		}
	}

	public List<Middleware> getMiddlewares() {
		if (this.middlewares == null) {
			return new LinkedList<Middleware>();
		} else {
			return middlewares;
		}
	}

	public List<Framework> getFrameworks() {
		if (this.frameworks == null) {
			return new LinkedList<Framework>();
		} else {
			return frameworks;
		}
	}

	public Services getServices() {
		if (this.services == null) {
			return new Services();
		} else {
			return services;
		}
	}

	public String getExtensible() {
		if (this.extensible == null) {
			return "unknown";
		} else {
			return extensible;
		}
	}

	public List<Infrastructure> getInfrastructures() {
		if (this.infrastructures == null) {
			return new LinkedList<Infrastructure>();
		} else {
			return infrastructures;
		}
	}

	@Override
	public String toString() {
		return "PaasProfile [name=" + name + ", revision=" + revision + ", url=" + url + ", status=" + status
				+ ", statusSince=" + statusSince + ", type=" + type + ", platform=" + platform + ", hosting=" + hosting
				+ ", pricings=" + pricings + ", qos=" + qos + ", scaling=" + scaling + ", runtimes=" + runtimes
				+ ", middlewares=" + middlewares + ", frameworks=" + frameworks + ", services=" + services
				+ ", extensible=" + extensible + ", infrastructures=" + infrastructures + ", failed=" + failed + "]";
	}

}
