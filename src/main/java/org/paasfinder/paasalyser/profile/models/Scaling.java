package org.paasfinder.paasalyser.profile.models;

import org.paasfinder.paasalyser.gsonutility.deserializers.ScalingDeserializer;

import com.google.gson.annotations.JsonAdapter;

public class Scaling {

	private boolean vertical;
	private boolean horizontal;
	private boolean auto;

	public Scaling(boolean vertical, boolean horizontal, boolean auto) {
		super();
		this.vertical = vertical;
		this.horizontal = horizontal;
		this.auto = auto;
	}

	public boolean getVertical() {
		return vertical;
	}

	public boolean getHorizontal() {
		return horizontal;
	}

	public boolean getAuto() {
		return auto;
	}

}