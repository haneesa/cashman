package com.suncorp.api.cashman.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.suncorp.api.cashman.entity.CurrencyNote;
import com.suncorp.api.cashman.exception.CurrencyNotAvailableException;
import com.suncorp.api.cashman.service.CurrencyDispenserService;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyNotesTest {

	@InjectMocks
	private CurrencyNotes currencyNotes;

	@Mock
	private CurrencyDispenserService mockDispenserService;

	@Test
	public void shouldReturnBadRequestWhenAmountIsNotNumber() {
		assertEquals(HttpStatus.BAD_REQUEST.value(), currencyNotes.dispenseCurrencyNotes("Ten").getStatus());
	}

	@Test
	public void shouldReturnBadRequestWhenNotesNotAvailable() {
		when(mockDispenserService.dispenseCurrency(2000)).thenThrow(new CurrencyNotAvailableException("Error"));
		assertEquals(HttpStatus.BAD_REQUEST.value(), currencyNotes.dispenseCurrencyNotes("2000").getStatus());
	}

	@Test
	public void shouldReturnSuccessForValidScenario() {
		List<CurrencyNote> notes = new ArrayList<>();
		notes.add(new CurrencyNote("20-2", 20, 2));
		when(mockDispenserService.dispenseCurrency(40)).thenReturn(notes);
		assertEquals(HttpStatus.OK.value(), currencyNotes.dispenseCurrencyNotes("40").getStatus());
	}

	@Test
	public void shouldReturnBadRequestWhenNumberOfNotesIsInvalidForTwenty() {
		assertEquals(HttpStatus.BAD_REQUEST.value(), currencyNotes.loadCurrencyNotes("Twenty", "Ten").getStatus());
	}

	@Test
	public void shouldReturnSuccessForValidNumberOfNotesForTwenty() {
		assertEquals(HttpStatus.NO_CONTENT.value(), currencyNotes.loadCurrencyNotes("20", "10").getStatus());
	}

	@Test
	public void shouldReturnBadRequestWhenNumberOfNotesIsInvalidForFifty() {
		assertEquals(HttpStatus.BAD_REQUEST.value(), currencyNotes.loadCurrencyNotes("50", "Fifty").getStatus());
	}

	@Test
	public void shouldReturnSuccessForValidNumberOfNotesForFifty() {
		assertEquals(HttpStatus.NO_CONTENT.value(), currencyNotes.loadCurrencyNotes("50", "10").getStatus());
	}

	@Test
	public void shouldReturnSuccessForFetchThreshold() {
		List<CurrencyNote> notes = new ArrayList<>();
		notes.add(new CurrencyNote("20-2", 20, 2));
		notes.add(new CurrencyNote("50-3", 50, 3));
		when(mockDispenserService.fetchThreshold()).thenReturn(notes);
		assertEquals(HttpStatus.OK.value(), currencyNotes.fetchThreshold().getStatus());
	}
}
