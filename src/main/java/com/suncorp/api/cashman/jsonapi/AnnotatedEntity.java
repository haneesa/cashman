package com.suncorp.api.cashman.jsonapi;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.suncorp.api.cashman.jsonapi.annotations.Id;
import com.suncorp.api.cashman.jsonapi.annotations.Type;
import com.suncorp.api.cashman.jsonapi.model.Entity;
import com.suncorp.api.cashman.jsonapi.model.ObjectField;
import com.suncorp.api.cashman.jsonapi.model.StringField;

import lombok.Data;

/**
 * Annotated Entity which will be used for building response.
 *
 * @author Haneesa Sulfikhar (haneesa)
 */
@Data
public class AnnotatedEntity {
	private Entity entity;
	private String id;
	private String type;
	private Map<String, Object> attributes = new HashMap<String, Object>();

	public AnnotatedEntity(Entity entity) {
		Type entityType = entity.getClass().getDeclaredAnnotation(Type.class);
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				id = new StringField(field).get(entity);
			} else {
				String fieldName = field.getName();
				attributes.put(fieldName, new ObjectField(field).get(entity));
			}
		}
		this.type = entityType.value();
	}

}
