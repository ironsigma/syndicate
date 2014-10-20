package com.hawkprime.syndicate.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Sha1Test {
	
	@Test
	public void testSha1() {
		assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709",
				Sha1.digest(""));

		assertEquals("2fd4e1c67a2d28fced849ee1bb76e7391b93eb12",
				Sha1.digest("The quick brown fox jumps over the lazy dog"));
	}

}
