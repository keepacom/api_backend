package com.keepa.api.backend.structs;

import static com.keepa.api.backend.helper.Utility.gsonPretty;

/**
 * Contains information about an API error.
 */
public class RequestError {
	String type, message, details;

	@Override
	public String toString() {
		return gsonPretty.toJson(this);
	}
}
