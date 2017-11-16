package com.suncorp.api.cashman.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.suncorp.api.cashman.entity.CurrencyNote;
import com.suncorp.api.cashman.exception.CurrencyNotAvailableException;

import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for currency notes
 * 
 * @author Haneesa Sulfikhar (haneesa)
 */
@Slf4j
@Service
public class CurrencyDispenserService {

	private int twenties;
	private int fifties;

	/** Initialization of currency */
	@PostConstruct
	public void postConstruct() {
		twenties = 10;
		log.info("Initialized twenties to 10");
		fifties = 10;
		log.info("Initialized fifties to 10");
	}

	public List<CurrencyNote> dispenseCurrency(int amount) {
		if (amountNotAvailable(amount)) {
			throw new CurrencyNotAvailableException("Amount not available");
		}

		List<CurrencyNote> notes = new ArrayList<>();
		CurrencyNote note = new CurrencyNote();
		note.setCurrencyValue(20);
		note.setNumberOfNotes(2);
		note.setId(String.format("%s-%s", note.getCurrencyValue(), note.getNumberOfNotes()));
		notes.add(note);
		return notes;
	}

	private boolean amountNotAvailable(int amount) {
		return amount > twenties * 20 + fifties * 50;
	}

	public void loadCurrency(String currencyType, int numberOfNotes) {
		if ("twenties".equalsIgnoreCase(currencyType)) {
			twenties = twenties + numberOfNotes;
		} else if ("fifties".equalsIgnoreCase(currencyType)) {
			fifties = fifties + numberOfNotes;
		}
	}

}
