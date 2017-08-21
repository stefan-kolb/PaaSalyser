package org.paasfinder.paasalyser.profile.models;

import com.google.gson.annotations.SerializedName;

public class Services {

	@SerializedName("native")
	private NativeService[] _native;
	private AddonService[] addon;

	public Services(NativeService[] _native, AddonService[] addon) {
		super();
		this._native = _native;
		this.addon = addon;
	}

	public NativeService[] getNative() {
		return _native;
	}

	public AddonService[] getAddon() {
		return addon;
	}

}