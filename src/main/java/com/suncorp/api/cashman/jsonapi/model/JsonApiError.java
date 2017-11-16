package com.suncorp.api.cashman.jsonapi.model;

import lombok.Data;

/**
 * @author Haneesa Sulfikhar (haneesa)
 */
@Data
public class JsonApiError {
	private String id;
	private String status;
	private String code;
	private String title;
	private String detail;
	private Source source;
	private Object meta;

	@Data
	public static class Source {
		private String pointer;
		private String parameter;
	}
}
