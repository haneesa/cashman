package com.suncorp.api.cashman.jsonapi.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.suncorp.api.cashman.jsonapi.EntityBuilder;

import lombok.Data;

/**
 * @author Haneesa Sulfikhar (haneesa)
 */
@Data
public class JsonApiResponse {

	private JsonApi jsonapi = new JsonApi();
	private JsonElement data;

	public JsonApiResponse(List<? extends Entity> entities) {
		data(entities);
	}

	public void data(List<? extends Entity> entities) {
		JsonArray jsonArray = new JsonArray();
		for (Entity entity : entities) {
			jsonArray.add(new EntityBuilder(entity).build());
		}
		this.data = (JsonElement) jsonArray;
	}

	@Data
	public static class JsonApi {
		private String version = "1.0";
	}

	public String jsonResponse() {
		return new Gson().toJson(this);
	}
}