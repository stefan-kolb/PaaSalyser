package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.PaasProfile;
import org.paasfinder.paasalyser.profile.models.Hosting;
import org.paasfinder.paasalyser.profile.models.Infrastructure;
import org.paasfinder.paasalyser.profile.models.Pricing;
import org.paasfinder.paasalyser.profile.models.Runtime;
import org.paasfinder.paasalyser.profile.models.Scaling;
import org.paasfinder.paasalyser.profile.models.Services;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class PaasProfileDeserializer implements JsonDeserializer<PaasProfile> {

	@Override
	public PaasProfile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		// Required fields
		String name = jsonObject.get("name").getAsString();
		String revision = jsonObject.get("revision").getAsString();
		String url = jsonObject.get("url").getAsString();
		String status = jsonObject.get("status").getAsString();
		String type = jsonObject.get("type").getAsString();

		Hosting hosting = null;
		try {
			hosting = context.deserialize(jsonObject.get("hosting"), Hosting.class);
		} catch (JsonParseException e) {
			if (jsonObject.get("hosting").isJsonArray()) {
				JsonArray array = json.getAsJsonArray();
			}
		}

		boolean extensible = jsonObject.get("extensible").getAsBoolean();

		// Optional fields
		String statusSince = jsonObject.get("status_since").getAsString();

		String platform;
		try {
			platform = jsonObject.get("platform").getAsString();
		} catch (NullPointerException e) {
			platform = null;
		}

		Pricing[] pricings;
		try {
			pricings = context.deserialize(jsonObject.get("pricings"), Pricing.class);
		} catch (NullPointerException e) {
			pricings = null;
		}

		Scaling scaling;
		try {
			scaling = context.deserialize(jsonObject.get("scaling"), Scaling.class);
		} catch (NullPointerException e) {
			scaling = null;
		}

		Runtime[] runtimes;
		try {
			runtimes = context.deserialize(jsonObject.get("runtimes"), Runtime.class);
		} catch (NullPointerException e) {
			runtimes = null;
		}

		Services services;
		try {
			services = context.deserialize(jsonObject.get("services"), Services.class);
		} catch (NullPointerException e) {
			services = null;
		}

		Infrastructure[] infrastructures;
		try {
			infrastructures = context.deserialize(jsonObject.get("infrastructures"), Infrastructure.class);
		} catch (NullPointerException e) {
			infrastructures = null;
		}

		return new PaasProfile(name, revision, url, status, statusSince, type, platform, hosting, pricings, scaling,
				runtimes, services, extensible, infrastructures);
	}

}
