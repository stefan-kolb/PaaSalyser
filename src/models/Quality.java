package models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quality {

	@SerializedName("uptime")
	@Expose
	private float uptime;

	@SerializedName("compliance")
	@Expose
	private List<String> compliance = null;

	public float getUptime() {
		return uptime;
	}

	public List<String> getCompliance() {
		return compliance;
	}
}
