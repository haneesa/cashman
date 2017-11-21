package com.suncorp.api.cashman.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

	/** Assumption - one transaction at a time */

	private static final int CONSTANT_10 = 10;

	private static final int CONSTANT_20 = 20;

	private static final int CONSTANT_50 = 50;

	private Map<Integer, Integer> cash = new ConcurrentHashMap<>();

	/** Initialization of currency */
	@PostConstruct
	public void postConstruct() {
		cash.put(CONSTANT_20, CONSTANT_10);
		log.info("Initialized twenties to 10");
		cash.put(CONSTANT_50, CONSTANT_10);
		log.info("Initialized fifties to 10");
	}

	public List<CurrencyNote> dispenseCurrency(int amount) {
		if (amountNotAvailable(amount)) {
			throw new CurrencyNotAvailableException("Amount not available");
		}
		List<CurrencyNote> notes = notes(amount);
		if (notes.isEmpty()) {
			throw new CurrencyNotAvailableException("Amount not available");
		} else {
			notes.forEach(note -> dispenseNotes(note.getCurrencyValue(), note.getNumberOfNotes()));
		}
		return notes;
	}

	private void dispenseNotes(int currencyValue, int numberOfNotes) {
		cash.put(currencyValue, cash.get(currencyValue) - numberOfNotes);
	}

	private static boolean notesAvailableFor(int notesAvailable, int numberOfNotesRequired) {
		return notesAvailable > numberOfNotesRequired;
	}

	private boolean amountNotAvailable(int amount) {
		return amount > availableCash();
	}

	private int availableCash() {
		return cash.get(CONSTANT_20) * CONSTANT_20 + cash.get(CONSTANT_50) * CONSTANT_50;
	}

	public void loadCurrency(int currency, int numberOfNotes) {
		cash.put(currency, cash.get(currency) + numberOfNotes);
	}

	public List<CurrencyNote> fetchThreshold() {
		List<CurrencyNote> notes = new ArrayList<>();
		notes.add(note(CONSTANT_50, cash.get(CONSTANT_50)));
		notes.add(note(CONSTANT_20, cash.get(CONSTANT_20)));
		return notes;
	}

	private static CurrencyNote note(int denomination, int numberOfNotes) {
		CurrencyNote note = new CurrencyNote();
		note.setCurrencyValue(denomination);
		note.setNumberOfNotes(numberOfNotes);
		note.setId();
		return note;
	}

	private List<CurrencyNote> notes(int amount) {
		boolean cashDispensed = false;
		List<CurrencyNote> notes = new ArrayList<>();
		if (amount >= CONSTANT_50) {
			int reminder = amount % CONSTANT_50;
			int numberOfFiftyNotes = (amount - reminder) / CONSTANT_50;
			do {
				if (notesAvailableFor(cash.get(CONSTANT_50), numberOfFiftyNotes)) {
					notes.add(note(CONSTANT_50, numberOfFiftyNotes));
					int remainingAmount = amount - (CONSTANT_50 * numberOfFiftyNotes);
					if (remainingAmount == 0) {
						cashDispensed = true;
						break;
					}
					if (remainingAmount >= CONSTANT_20) {
						if (remainingAmount % CONSTANT_20 == 0) {
							int numberOfTwentyNotes = remainingAmount / CONSTANT_20;
							if (notesAvailableFor(cash.get(CONSTANT_20), numberOfTwentyNotes)) {
								notes.add(note(CONSTANT_20, numberOfTwentyNotes));
								cashDispensed = true;
								break;
							}
						} else {
							notes.clear();
							numberOfFiftyNotes--;
						}
					} else {
						notes.clear();
						numberOfFiftyNotes--;
					}
				} else {
					notes.clear();
					numberOfFiftyNotes--;
				}
			} while (numberOfFiftyNotes >= 1);
		}
		if ((!cashDispensed) && amount % CONSTANT_20 == 0) {
			int numberOfTwentyNotes = amount / CONSTANT_20;
			if (notesAvailableFor(cash.get(CONSTANT_20), numberOfTwentyNotes)) {
				notes.add(note(CONSTANT_20, numberOfTwentyNotes));
			}
		}
		return notes;
	}

}
