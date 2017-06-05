package models;

import java.util.LinkedList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Runtime {

	@SerializedName("language")
	@Expose
	private String language;
	@SerializedName("versions")
	@Expose
	private List<String> versions = null;

	public String getLanguage() {
		return language;
	}

	public List<String> getVersions() {
		if (versions == null) {
			versions = new LinkedList<String>();
			versions.add("unknown");
			return versions;
		} else if (versions.isEmpty()) {
			versions.add("unknown");
			return versions;
		} else
			return versions;
	}

}