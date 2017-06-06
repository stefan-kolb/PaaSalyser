package models;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Services {

	@SerializedName("native")
	@Expose
	private List<NativeService> _native = null;
	@SerializedName("addon")
	@Expose
	private List<AddonService> addon = null;

	public List<NativeService> getNative() {
		if (_native != null) {
			return _native;
		} else {
			return new LinkedList<NativeService>();
		}
	}

	public List<AddonService> getAddon() {
		if (addon != null) {
			return addon;
		} else {
			return new LinkedList<AddonService>();
		}
	}

}