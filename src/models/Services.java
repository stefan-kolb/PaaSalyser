package models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Services {

	@SerializedName("native")
	@Expose
	private List<Object> _native = null;
	@SerializedName("addon")
	@Expose
	private List<Object> addon = null;

	public List<Object> getNative() {
		return _native;
	}

	public List<Object> getAddon() {
		return addon;
	}

}