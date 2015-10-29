package com.keepa.api.backend.exceptions;

/**
 * API-Exception thrown on different negative API-Events.
 */
public class KeepaAPIException extends Exception {
    public KeepaAPIException(String s) {
        super(s);
    }
}
