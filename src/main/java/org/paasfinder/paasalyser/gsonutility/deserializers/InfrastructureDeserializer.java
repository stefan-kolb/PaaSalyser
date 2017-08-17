package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.models.Infrastructure;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class InfrastructureDeserializer implements JsonDeserializer<Infrastructure> {

	@Override
	public Infrastructure deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final String continent = jsonObject.get("continent").getAsString();
		final String country = jsonObject.get("country").getAsString();
		final String region = jsonObject.get("region").getAsString();
		final String region_code = jsonObject.get("region_code").getAsString();
		final String provider = jsonObject.get("provider").getAsString();

		return new Infrastructure(continent, country, region, region_code, provider);
	}

}
