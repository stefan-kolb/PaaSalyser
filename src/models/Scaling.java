package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scaling {

	@SerializedName("vertical")
	@Expose
	private Boolean vertical;
	@SerializedName("horizontal")
	@Expose
	private Boolean horizontal;
	@SerializedName("auto")
	@Expose
	private Boolean auto;

	public Boolean getVertical() {
		return vertical;
	}

	public Boolean getHorizontal() {
		return horizontal;
	}

	public Boolean getAuto() {
		return auto;
	}

}