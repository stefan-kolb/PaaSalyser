package org.paasfinder.paasalyser.profile.models;

import org.paasfinder.paasalyser.gsonutility.deserializers.HostingDeserializer;

import com.google.gson.annotations.JsonAdapter;

public class Hosting {

	private boolean _public = false;
	private boolean _private = false;
	private boolean virtualPrivate = false;

	public Hosting(boolean _public, boolean _private, boolean virtualPrivate) {
		super();
		this._public = _public;
		this._private = _private;
		this.virtualPrivate = virtualPrivate;
	}

	public boolean getPublic() {
		return _public;
	}

	public boolean getPrivate() {
		return _private;
	}

	public boolean getVirtualPrivate() {
		return virtualPrivate;
	}

}
