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

	public Boolean get_virtual_private() {
		return _virtual_private;
	}

	public void set_virtual_private(Boolean _virtual_private) {
		this._virtual_private = _virtual_private;
	}

	public Boolean getPublic() {
		return _public;
	}

	public void setPublic(Boolean _public) {
		this._public = _public;
	}

	public Boolean getPrivate() {
		return _private;
	}

	public void setPrivate(Boolean _private) {
		this._private = _private;
	}

}