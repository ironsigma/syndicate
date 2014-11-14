package com.hawkprime.syndicate.adapter;

/**
 * Exception for conversion errors.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
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
