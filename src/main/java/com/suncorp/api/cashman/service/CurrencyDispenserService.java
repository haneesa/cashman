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

		if (isAmountMultipleOf(50, amount) && NotesAvailableFor(fifties, amount / 50)) {
			CurrencyNote note = new CurrencyNote();
			note.setCurrencyValue(50);
			note.setNumberOfNotes(amount / 50);
			note.setId();
			notes.add(note);
		} else if (isAmountMultipleOf(20, amount) && NotesAvailableFor(twenties, amount / 20)) {
			CurrencyNote note = new CurrencyNote();
			note.setCurrencyValue(20);
			note.setNumberOfNotes(amount / 20);
			note.setId();
			notes.add(note);
		} else {
			throw new CurrencyNotAvailableException("Amount not available");
		}
		return notes;
	}

	private boolean NotesAvailableFor(int notesAvailable, int numberOfNotesRequired) {
		return notesAvailable > numberOfNotesRequired;
	}

	private boolean isAmountMultipleOf(int denomination, int amount) {
		return amount % denomination == 0;
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
