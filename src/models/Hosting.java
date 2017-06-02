package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hosting {

	@SerializedName("public")
	@Expose
	private Boolean _public;
	@SerializedName("private")
	@Expose
	private Boolean _private;
	@SerializedName("virtual_private")
	@Expose
	private Boolean _virtual_private;

	public Boolean getVirtualPrivate() {
		return _virtual_private;
	}

	public Boolean getPublic() {
		return _public;
	}

	public Boolean getPrivate() {
		return _private;
	}

}