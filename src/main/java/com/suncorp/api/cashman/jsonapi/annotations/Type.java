package com.suncorp.api.cashman.jsonapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation Type for identifying type in the Entity.
 *
 * @author Haneesa Sulfikhar (haneesa)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Type {
	String value();
}
