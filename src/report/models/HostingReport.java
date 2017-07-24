package report.models;

public class HostingReport {

	private double privateHostingPercent = 0.0;
	private double publicHostingPercent = 0.0;
	private double virtualPrivateHostingPercent = 0.0;

	public HostingReport(double privateHostingPercent, double publicHostingPercent,
			double virtualPrivateHostingPercent) {
		super();
		this.privateHostingPercent = privateHostingPercent;
		this.publicHostingPercent = publicHostingPercent;
		this.virtualPrivateHostingPercent = virtualPrivateHostingPercent;
	}

	public double getPrivateHostingPercent() {
		return privateHostingPercent;
	}

	public double getPublicHostingPercent() {
		return publicHostingPercent;
	}

	public double getVirtualPrivateHostingPercent() {
		return virtualPrivateHostingPercent;
	}

}
