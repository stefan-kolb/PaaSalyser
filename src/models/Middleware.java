package models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Middleware {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("runtime")
	@Expose
	private String runtime;
	@SerializedName("versions")
	@Expose
	private List<String> versions = null;

	public String getName() {
		return name;
	}

	public String getRuntime() {
		return runtime;
	}

	public List<String> getVersions() {
		return versions;
	}

}