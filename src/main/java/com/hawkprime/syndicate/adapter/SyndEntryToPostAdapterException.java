package com.hawkprime.syndicate.adapter;

/**
 * Exception for conversion errors.
 */
public class SyndEntryToPostAdapterException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param message Message
	 */
	public SyndEntryToPostAdapterException(final String message) {
		super(message);
	}
}
