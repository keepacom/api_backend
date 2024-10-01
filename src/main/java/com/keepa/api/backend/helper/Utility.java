package com.keepa.api.backend.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;

/**
 * Static helper methods and reused objects.
 */
public class Utility {
public static final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.PRIVATE, Modifier.PROTECTED).create();

public static final Gson gsonPretty = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.PRIVATE, Modifier.PROTECTED).setPrettyPrinting().create();

public static String arrayToCsv(String array[]) {
	StringBuilder buff = new StringBuilder();
	String sep = "";
	for (String s : array) {
		buff.append(sep);
		buff.append(s);
		sep = ",";
	}
	return buff.toString();
}

public static String urlEncodeUTF8(String s) {
	try {
		return URLEncoder.encode(s, "UTF-8");
	} catch (UnsupportedEncodingException e) {
		throw new UnsupportedOperationException(e);
	}
}
}
