package org.paasfinder.paasalyser.profile.models;

import com.google.gson.annotations.SerializedName;

public class Hosting {

	@SerializedName("public")
	private Boolean _public = false;
	@SerializedName("private")
	private Boolean _private = false;
	@SerializedName("virtual_private")
	private Boolean virtualPrivate = false;

	public Hosting(Boolean _public, Boolean _private, Boolean virtualPrivate) {
		super();
		this._public = _public;
		this._private = _private;
		this.virtualPrivate = virtualPrivate;
	}

	public boolean getPublic() {
		if (_public == null)
			return false;
		return _public.booleanValue();
	}

	public boolean getPrivate() {
		if (_private == null)
			return false;
		return _private.booleanValue();
	}

	public boolean getVirtualPrivate() {
		if (virtualPrivate == null)
			return false;
		return virtualPrivate.booleanValue();
	}

}
