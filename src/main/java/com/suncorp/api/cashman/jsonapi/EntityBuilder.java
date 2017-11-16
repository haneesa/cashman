package com.suncorp.api.cashman.jsonapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.suncorp.api.cashman.jsonapi.model.Entity;

/**
 * Entity builder for response building.
 *
 * @author Haneesa Sulfikhar (haneesa)
 */
public class EntityBuilder {
	private static final Gson GSON = new GsonBuilder().serializeNulls().create();
	private AnnotatedEntity entity;

	public EntityBuilder(Entity entity) {
		this.entity = new AnnotatedEntity(entity);
	}

	public JsonElement build() {
		if (entity == null) {
			throw new IllegalStateException("Entity cannot be null");
		}
		JsonObject jsonEntity = new JsonObject();
		jsonEntity.addProperty("id", entity.getId());
		jsonEntity.addProperty("type", entity.getType());
		jsonEntity.add("attributes", GSON.toJsonTree(entity.getAttributes()));

		return jsonEntity;
	}

}
