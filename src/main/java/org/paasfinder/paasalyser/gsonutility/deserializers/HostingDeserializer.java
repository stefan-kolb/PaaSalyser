package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.models.Hosting;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class HostingDeserializer implements JsonDeserializer<Hosting> {

	@Override
	public Hosting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		boolean _public = jsonObject.get("public").getAsBoolean();
		boolean _private = jsonObject.get("private").getAsBoolean();
		boolean virtual_private;
		try {
			virtual_private = jsonObject.get("virtual_private").getAsBoolean();
		} catch (NullPointerException e) {
			virtual_private = false;
		}

		return new Hosting(_public, _private, virtual_private);
	}

}
