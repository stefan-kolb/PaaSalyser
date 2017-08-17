package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.models.AddonService;
import org.paasfinder.paasalyser.profile.models.NativeService;
import org.paasfinder.paasalyser.profile.models.Services;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ServicesDeserializer implements JsonDeserializer<Services> {

	@Override
	public Services deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final NativeService[] nativeServices = context.deserialize(jsonObject.get("natives"), NativeService.class);
		final AddonService[] addonServices = context.deserialize(jsonObject.get("natives"), AddonService.class);
		return new Services(nativeServices, addonServices);
	}

}
