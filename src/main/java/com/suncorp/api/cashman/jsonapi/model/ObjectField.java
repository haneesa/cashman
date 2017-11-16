package com.suncorp.api.cashman.jsonapi.model;

import java.lang.reflect.Field;

/**
 * @author Haneesa Sulfikhar (haneesa)
 */
public class ObjectField {

	private Field field;

	public ObjectField(Field field) {
		this.field = field;
	}

	public Object get(Object object) {
		Object value = null;
		field.setAccessible(true);
		try {
			value = field.get(object);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			// Do nothing!
		}

		return value;
	}
}
