package com.suncorp.api.cashman;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.suncorp.api.cashman.resource.CurrencyNotes;

/**
 * Configuration for Jersey setup
 *
 * @author Haneesa Sulfikhar (haneesa)
 */
@Configuration
public class JerseyConfiguration extends ResourceConfig {

	/** Constructor to initialize ResourceConfig for Jersey to use */
	public JerseyConfiguration() {
		register(CurrencyNotes.class);
	}
}
