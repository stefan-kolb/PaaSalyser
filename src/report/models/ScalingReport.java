package report.models;

public class ScalingReport {

    private double verticalPercent = 0.0;
    private double horizontalPercent = 0.0;
    private double autoPercent = 0.0;

    public ScalingReport(double verticalPercent, double horizontalPercent,
	    double autoPercent) {
	super();
	this.verticalPercent = verticalPercent;
	this.horizontalPercent = horizontalPercent;
	this.autoPercent = autoPercent;
    }

    public double getVerticalPercent() {
	return verticalPercent;
    }

    public double getHorizontalPercent() {
	return horizontalPercent;
    }

    public double getAutoPercent() {
	return autoPercent;
    }

}
