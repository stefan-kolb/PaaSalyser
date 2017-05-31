package models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Framework {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("runtime")
	@Expose
	private String runtime;
	@SerializedName("versions")
	@Expose
	private List<Object> versions = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public List<Object> getVersions() {
		return versions;
	}

	public void setVersions(List<Object> versions) {
		this.versions = versions;
	}

}