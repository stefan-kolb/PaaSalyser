package profile.models;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NativeService {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("versions")
	@Expose
	private List<String> versions = null;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getVersions() {
		if (versions != null) {
			return versions;
		} else {
			return new LinkedList<String>();
		}
	}

}
