package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.models.Scaling;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ScalingDeserializer implements JsonDeserializer<Scaling> {

	@Override
	public Scaling deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		return new Scaling(
				jsonObject.get("vertical").getAsBoolean(), 
				jsonObject.get("horizontal").getAsBoolean(),
				jsonObject.get("auto").getAsBoolean());
	}

}
