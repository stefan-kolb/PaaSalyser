package org.paasfinder.paasalyser.gsonutility.deserializers;

import java.lang.reflect.Type;

import org.paasfinder.paasalyser.profile.models.Pricing;
import org.paasfinder.paasalyser.profile.models.PricingModel;
import org.paasfinder.paasalyser.profile.models.PricingPeriod;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class PricingDeserializer implements JsonDeserializer<Pricing> {

	@Override
	public Pricing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		String modelString = jsonObject.get("model").getAsString();
		PricingModel model = null;
		if (modelString.equals("fixed")) {
			model = PricingModel.fixed;
		} else if (modelString.equals("metered")) {
			model = PricingModel.metered;
		} else if (modelString.equals("hybrid")) {
			model = PricingModel.hybrid;
		} else if (modelString.equals("free")) {
			model = PricingModel.free;
		}

		String periodString = jsonObject.get("period").getAsString();
		PricingPeriod period = null;
		if (periodString.equals("daily")) {
			period = PricingPeriod.daily;
		} else if (periodString.equals("monthly")) {
			period = PricingPeriod.monthly;
		} else if (periodString.equals("anually")) {
			period = PricingPeriod.anually;
		}

		return new Pricing(model, period);
	}

}
