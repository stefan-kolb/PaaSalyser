
package profile.models;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Qos {

	@SerializedName("uptime")
	@Expose
	private String uptime;
	@SerializedName("compliance")
	@Expose
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
