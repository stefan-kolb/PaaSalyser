package org.paasfinder.paasalyser.profile;

import java.nio.file.Path;
import java.util.Arrays;

import org.paasfinder.paasalyser.profile.models.Hosting;
import org.paasfinder.paasalyser.profile.models.Infrastructure;
import org.paasfinder.paasalyser.profile.models.Pricing;
import org.paasfinder.paasalyser.profile.models.Runtime;
import org.paasfinder.paasalyser.profile.models.Scaling;
import org.paasfinder.paasalyser.profile.models.Services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaasProfile {

	private String name;

	private String revision;

	private String url;

	private String status;
	
	@SerializedName("status_since")
	private String statusSince;

	private String type;

	private String platform;

	private Hosting hosting;

	private Pricing[] pricings;

	private Scaling scaling;

	private Runtime[] runtimes;

	private Services services;

	private boolean extensible;

	private Infrastructure[] infrastructures;

	public PaasProfile(String name, String revision, String url, String status, String statusSince, String type,
			String platform, Hosting hosting, Pricing[] pricings, Scaling scaling, Runtime[] runtimes,
			Services services, boolean extensible, Infrastructure[] infrastructures) {
		super();
		this.name = name;
		this.revision = revision;
		this.url = url;
		this.status = status;
		this.statusSince = statusSince;
		this.type = type;
		this.platform = platform;
		this.hosting = hosting;
		this.pricings = pricings;
		this.scaling = scaling;
		this.runtimes = runtimes;
		this.services = services;
		this.extensible = extensible;
		this.infrastructures = infrastructures;
	}

	public PaasProfile(String name) {
		super();
		this.name = name;
	}

	/**
	 * Checks wether all necessarry fields is present or not. Specified in
	 * paas-profiles/app/models/vendor/*.
	 * 
	 * @param currentlyScannedPath
	 *            Path of profile that is currently being scanned.
	 * @return this if necessarry fields are not null.
	 * @throws NullPointerException
	 *             if a required field is null.
	 */
	public PaasProfile checkProfileValidity(Path currentlyScannedPath) throws NullPointerException {
		if (this.getName() == null) {
			throw new NullPointerException(
					"Name of profile " + currentlyScannedPath.getFileName().toString() + " is null");
		}
		if (this.getName().isEmpty()) {
			throw new NullPointerException(
					"Name of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		if (this.getRevision() == null) {
			throw new NullPointerException(
					"Revision of profile " + currentlyScannedPath.getFileName().toString() + " is null");
		}
		if (this.getRevision().isEmpty()) {
			throw new NullPointerException(
					"Revision of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		if (this.getStatus() == null) {
			throw new NullPointerException(
					"Status of profile " + currentlyScannedPath.getFileName().toString() + " is null");
		}
		if (this.getStatus().isEmpty()) {
			throw new NullPointerException(
					"Status of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		if (this.getType() == null) {
			throw new NullPointerException(
					"Type of profile " + currentlyScannedPath.getFileName().toString() + " is null");
		}
		if (this.getType().isEmpty()) {
			throw new NullPointerException(
					"Type of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		if (this.getPlatform() != null && this.getPlatform().isEmpty()) {
			throw new NullPointerException(
					"Platform of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		if (this.getHosting() == null) {
			throw new NullPointerException(
					"Status of profile " + currentlyScannedPath.getFileName().toString() + " is null");
		}

		if (this.getRuntimes() != null) {
			for (Runtime runtime : this.getRuntimes()) {
				if (runtime == null) {
					throw new NullPointerException(
							"Runtime of profile " + currentlyScannedPath.getFileName().toString() + " is null");
				}
			}
		}

		if (this.getInfrastructures() != null) {
			for (Infrastructure infra : this.getInfrastructures()) {
				if (infra.getContinent() == null) {
					throw new NullPointerException(
							"NativeService of profile " + currentlyScannedPath.getFileName().toString() + " is null");
				}
				if (infra.getRegion() == null || infra.getRegion().isEmpty()) {
					if (infra.getCountry() == null) {
						throw new NullPointerException("NativeService of profile "
								+ currentlyScannedPath.getFileName().toString() + " is null");
					}
				}
			}
		}

		return this;
	}

	public String getName() {
		return name;
	}

	public String getRevision() {
		return revision;
	}

	public String getUrl() {
		return url;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusSince() {
		return statusSince;
	}

	public String getType() {
		return type;
	}

	public String getPlatform() {
		return platform;
	}

	public Hosting getHosting() {
		return hosting;
	}

	public Pricing[] getPricings() {
		return pricings;
	}

	public Scaling getScaling() {
		return scaling;
	}

	public Runtime[] getRuntimes() {
		return runtimes;
	}

	public Services getServices() {
		return services;
	}

	public boolean isExtensible() {
		return extensible;
	}

	public Infrastructure[] getInfrastructures() {
		return infrastructures;
	}

	@Override
	public String toString() {
		return "PaasProfile [name=" + name + ", revision=" + revision + ", url=" + url + ", status=" + status
				+ ", statusSince=" + statusSince + ", type=" + type + ", platform=" + platform + ", hosting=" + hosting
				+ ", pricings=" + Arrays.toString(pricings) + ", scaling=" + scaling + ", runtimes="
				+ Arrays.toString(runtimes) + ", services=" + services + ", extensible=" + extensible
				+ ", infrastructures=" + Arrays.toString(infrastructures) + "]";
	}

}
