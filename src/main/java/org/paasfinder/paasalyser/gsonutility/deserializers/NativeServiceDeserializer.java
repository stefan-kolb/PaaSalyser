package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.models.NativeService;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@Deprecated
public class NativeServiceDeserializer implements JsonDeserializer<NativeService> {

	@Override
	public NativeService deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final String name = jsonObject.get("name").getAsString();
		final String type = jsonObject.get("type").getAsString();
		final String description = jsonObject.get("description").getAsString();
		final String[] versions = context.deserialize(jsonObject.get("versions"), String.class);

		return new NativeService(name, type, description, versions);
	}

}
