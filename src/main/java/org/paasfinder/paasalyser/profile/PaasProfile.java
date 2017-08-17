package org.paasfinder.paasalyser.profile;

import java.nio.file.Path;
import java.util.Objects;

import org.paasfinder.paasalyser.profile.models.Hosting;
import org.paasfinder.paasalyser.profile.models.Infrastructure;
import org.paasfinder.paasalyser.profile.models.Pricing;
import org.paasfinder.paasalyser.profile.models.Runtime;
import org.paasfinder.paasalyser.profile.models.Scaling;
import org.paasfinder.paasalyser.profile.models.Services;

public class PaasProfile {

	private String name;
	private String revision;
	private String url;
	private String status;
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

	/**
	 * Checks wether all necessarry fields is present or not. Specified in
	 * paas-profiles/app/models/vendor/vendor.rb.
	 * 
	 * @param currentlyScannedPath
	 *            Path of profile that is currently being scanned.
	 * @return true if necessarry fields are not null.
	 * @throws NullPointerException
	 *             if a required field is null.
	 */
	public boolean checkProfileValidity(Path currentlyScannedPath) throws NullPointerException {
		Objects.requireNonNull(this.getName());
		if (this.getName().isEmpty()) {
			throw new NullPointerException(
					"Name of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		Objects.requireNonNull(this.getStatus());
		if (this.getStatus().isEmpty()) {
			throw new NullPointerException(
					"Status of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		Objects.requireNonNull(this.getRevision());
		if (this.getRevision().isEmpty()) {
			throw new NullPointerException(
					"Revision of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		Objects.requireNonNull(this.getUrl());
		if (this.getUrl().isEmpty()) {
			throw new NullPointerException(
					"URL of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		Objects.requireNonNull(this.getStatus());
		if (this.getStatus().isEmpty()) {
			throw new NullPointerException(
					"Status of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		Objects.requireNonNull(this.getType());
		if (this.getType().isEmpty()) {
			throw new NullPointerException(
					"Type of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		Objects.requireNonNull(this.isExtensible());

		Objects.requireNonNull(this.getHosting());

		if (this.getPlatform() != null && this.getPlatform().isEmpty()) {
			throw new NullPointerException(
					"Platform of profile " + currentlyScannedPath.getFileName().toString() + " is empty");
		}

		return true;
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

}
