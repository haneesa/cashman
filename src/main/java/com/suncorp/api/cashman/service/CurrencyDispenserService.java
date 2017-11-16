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

	private static final int CONSTANT_20 = 20;
	private static final int CONSTANT_50 = 50;
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
		if (isAmountMultipleOf(CONSTANT_50, amount) && NotesAvailableFor(fifties, amount / CONSTANT_50)) {
			int numberOfNotes = amount / CONSTANT_50;
			notes.add(note(CONSTANT_50, numberOfNotes));
			fifties = fifties - numberOfNotes;
		} else if (isAmountMultipleOf(CONSTANT_20, amount) && NotesAvailableFor(twenties, amount / CONSTANT_20)) {
			int numberOfNotes = amount / CONSTANT_20;
			notes.add(note(CONSTANT_20, numberOfNotes));
			twenties = twenties - numberOfNotes;
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
		return amount > twenties * CONSTANT_20 + fifties * CONSTANT_50;
	}

	public void loadCurrency(String currencyType, int numberOfNotes) {
		if ("twenties".equalsIgnoreCase(currencyType)) {
			twenties = twenties + numberOfNotes;
		} else if ("fifties".equalsIgnoreCase(currencyType)) {
			fifties = fifties + numberOfNotes;
		}
	}

	public List<CurrencyNote> fetchThreshold() {
		List<CurrencyNote> notes = new ArrayList<>();
		notes.add(note(CONSTANT_50, fifties));
		notes.add(note(CONSTANT_20, twenties));
		return notes;
	}

	private CurrencyNote note(int denomination, int numberOfNotes) {
		CurrencyNote note = new CurrencyNote();
		note.setCurrencyValue(denomination);
		note.setNumberOfNotes(numberOfNotes);
		note.setId();
		return note;
	}

}
