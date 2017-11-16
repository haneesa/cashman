package com.suncorp.api.cashman.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.suncorp.api.cashman.entity.CurrencyNote;
import com.suncorp.api.cashman.exception.CurrencyNotAvailableException;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyDispenserServiceTest {

	@InjectMocks
	private CurrencyDispenserService dispenserService;

	@Test(expected = CurrencyNotAvailableException.class)
	public void shouldThrowExceptionWhenAmountIsMoreThanFund() {
		dispenserService.postConstruct();
		dispenserService.dispenseCurrency(2000);
	}

	@Test
	public void shouldReturnValueWhenAmountIsLessThanFundMultipleOfTwenty() {
		dispenserService.postConstruct();
		List<CurrencyNote> notes = dispenserService.dispenseCurrency(40);
		assertEquals(2, notes.get(0).getNumberOfNotes());
		assertEquals(20, notes.get(0).getCurrencyValue());
	}

	@Test
	public void shouldReturnValueWhenAmountIsLessThanFundMultipleOfFifty() {
		dispenserService.postConstruct();
		List<CurrencyNote> notes = dispenserService.dispenseCurrency(150);
		assertEquals(3, notes.get(0).getNumberOfNotes());
		assertEquals(50, notes.get(0).getCurrencyValue());
	}

	@Test(expected = CurrencyNotAvailableException.class)
	public void shouldThrowExceptionWhenAmountCannotBeDispensed() {
		dispenserService.postConstruct();
		dispenserService.dispenseCurrency(30);
	}
}