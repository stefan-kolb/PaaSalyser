
package org.paasfinder.paasalyser.profile.models;

import java.util.LinkedList;
import java.util.List;

public class Qos {

	private String uptime;
	private List<String> compliance = null;

	public double getUptime() {
		if (uptime != null)
			return Double.parseDouble(uptime);
		else
			return Double.NaN;
	}

	public List<String> getCompliance() {
		if (compliance != null) {
			return compliance;
		} else {
			return new LinkedList<String>();
		}
	}

}
