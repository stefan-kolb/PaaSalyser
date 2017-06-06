package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddonService {

	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("type")
	@Expose
	private String type;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

}
