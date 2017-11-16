package com.suncorp.api.cashman.jsonapi.model;

import java.lang.reflect.Field;

/**
 * @author Haneesa Sulfikhar (haneesa)
 */
public class StringField {
	private Field field;

	public StringField(Field field) {
		this.field = field;
	}

	public String get(Object object) {
		Object value = null;
		field.setAccessible(true);
		try {
			value = field.get(object);

		} catch (IllegalAccessException | IllegalArgumentException e) {
			// Do nothing!
		}

		return (String) value;
	}
}
