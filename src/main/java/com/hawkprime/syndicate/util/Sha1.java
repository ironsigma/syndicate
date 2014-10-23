package com.hawkprime.syndicate.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SHA1 Message Digest.
 */
public final class Sha1 {
	private static final Logger LOG = LoggerFactory.getLogger(Sha1.class);
	private static MessageDigest messageDigest;

	private Sha1() { /* empty */ }

	public static String digest(final String string) {
		try {
			if (messageDigest == null) {
				messageDigest = MessageDigest.getInstance("SHA1");
			}
			return String.format("%040x", new BigInteger(1, messageDigest.digest(string.getBytes("UTF-8"))));
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Unable to create message digest.");
		} catch (UnsupportedEncodingException e) {
			LOG.error("Invalid string encoding");
		}
		return null;
	}
}
