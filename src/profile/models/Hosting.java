package profile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hosting {

	@SerializedName("public")
	@Expose
	private Boolean _public = false;
	@SerializedName("private")
	@Expose
	private Boolean _private = false;
	@SerializedName("virtual_private")
	@Expose
	private Boolean virtualPrivate = false;

	public Boolean getPublic() {
		return _public;
	}

	public Boolean getPrivate() {
		return _private;
	}

	public Boolean getVirtualPrivate() {
		return virtualPrivate;
	}

}
