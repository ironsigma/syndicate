package com.hawkprime.syndicate.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Sha1 {
	private static final Logger LOG = LoggerFactory.getLogger(Sha1.class);
	private static MessageDigest messageDigest;

	private static void initMessageDigest() {
		try {
			messageDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Unable to create message digest.");
		}
	}
	
	public static String digest(String string) {
		if (messageDigest == null ){
			initMessageDigest();
		}
		try {
			return String.format("%040x", new BigInteger(1, messageDigest.digest(string.getBytes("UTF-8"))));
		} catch (UnsupportedEncodingException e) {
			LOG.error("Invalid string encoding");
		}
		return null;
	}
}
