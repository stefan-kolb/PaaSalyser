package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.models.Hosting;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class HostingDeserializer implements JsonDeserializer<Hosting> {

	@Override
	public Hosting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (json.isJsonArray()) {
			JsonArray hostingArray = json.getAsJsonArray();
			boolean _public = false;
			boolean _private = false;
			for (JsonElement elem : hostingArray) {
				String elemString = elem.getAsString();
				if(elemString.equals("public")){
					_public = true;
				} else if (elemString.equals("private")){
					_private = true;
				} else {
					// TODO JsonSyntaxException
				}
			}
			return new Hosting(_public, _private, null);
		}
		if (json.isJsonObject()) {
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
		return null;
	}

}
