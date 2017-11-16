package com.suncorp.api.cashman.entity;

import com.suncorp.api.cashman.jsonapi.annotations.Id;
import com.suncorp.api.cashman.jsonapi.annotations.Type;
import com.suncorp.api.cashman.jsonapi.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Haneesa Sulfikhar (haneesa)
 */
@Data
@Type("currency-notes")
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyNote implements Entity {

	@Id
	private String id;

	private int currencyValue;

	private int numberOfNotes;

	public void setId() {
		this.id = String.format("%s-%s", currencyValue, numberOfNotes);
	}
}
