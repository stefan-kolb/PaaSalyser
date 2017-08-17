package org.paasfinder.paasalyser.profile.models;

import org.paasfinder.paasalyser.gsonutility.deserializers.ServicesDeserializer;

import com.google.gson.annotations.JsonAdapter;

public class Services {

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