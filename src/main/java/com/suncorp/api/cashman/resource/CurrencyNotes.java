package com.suncorp.api.cashman.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suncorp.api.cashman.entity.CurrencyNote;
import com.suncorp.api.cashman.exception.CurrencyNotAvailableException;
import com.suncorp.api.cashman.jsonapi.model.JsonApiError;
import com.suncorp.api.cashman.jsonapi.model.JsonApiResponse;
import com.suncorp.api.cashman.service.CurrencyDispenserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Resource implementation for currency notes
 * 
 * @author Haneesa Sulfikhar (haneesa)
 */
@Slf4j
@Component
@Path("/currency-notes")
@Consumes("application/vnd.api+json")
@Produces("application/vnd.api+json")
public class CurrencyNotes {

	private static final String CONSTANT_50 = "50";
	private static final String CONSTANT_20 = "20";

	@Autowired
	private CurrencyDispenserService dispenserService;

	@GET
	public Response dispenseCurrencyNotes(@QueryParam("amount") String amount) {
		log.info("Request for amount: {}", amount);
		try {
			if (StringUtils.isNumeric(amount)) {
				List<CurrencyNote> notes = dispenserService.dispenseCurrency(Integer.valueOf(amount));
				return Response.ok(new JsonApiResponse(notes).jsonResponse()).build();
			} else {
				return badRequest("error.client.amount.invalid");
			}
		} catch (CurrencyNotAvailableException exception) {
			return badRequest("error.client.notes.not-available");
		}
	}

	@POST
	@Path("{currency}/{numberOfNotes}")
	public Response loadCurrencyNotes(@PathParam("currency") String currency,
			@PathParam("numberOfNotes") String numberOfNotes) {
		log.info("Request for load {} denomination: {}", currency, numberOfNotes);
		if (isAcceptable(currency) && StringUtils.isNumeric(numberOfNotes)) {
			dispenserService.loadCurrency(Integer.valueOf(currency), Integer.valueOf(numberOfNotes));
			return Response.noContent().build();
		} else {
			return badRequest("error.client.number-of-notes.invalid");
		}
	}

	@GET
	@Path("threshold")
	public Response fetchThreshold() {
		List<CurrencyNote> notes = dispenserService.fetchThreshold();
		return Response.ok(new JsonApiResponse(notes).jsonResponse()).build();
	}

	private Response badRequest(String message) {
		return Response.status(Status.BAD_REQUEST).entity(new JsonApiError("400", message)).build();
	}

	private boolean isAcceptable(String currency) {
		return CONSTANT_20.equals(currency) || CONSTANT_50.equals(currency);
	}
}
