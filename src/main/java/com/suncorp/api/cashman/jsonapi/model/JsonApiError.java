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

	public JsonApiError(String status, String code) {
		this.status = status;
		this.code = code;
	}
}
