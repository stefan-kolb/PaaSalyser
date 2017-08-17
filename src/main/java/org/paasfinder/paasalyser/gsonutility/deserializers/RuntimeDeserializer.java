package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.models.Runtime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class RuntimeDeserializer implements JsonDeserializer<Runtime> {

	@Override
	public Runtime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		final String language = jsonObject.get("language").getAsString();
		final String[] versions = context.deserialize(jsonObject.get("versions"), String.class);
		return new Runtime(language, versions);
	}

}
