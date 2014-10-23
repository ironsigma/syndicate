package com.hawkprime.syndicate.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * SHA1 Tests.
 */
public class Sha1Test {

	@Test
	public void testSha1() {
		assertThat(Sha1.digest(""), is("da39a3ee5e6b4b0d3255bfef95601890afd80709"));

		assertThat(Sha1.digest("The quick brown fox jumps over the lazy dog"),
				is("2fd4e1c67a2d28fced849ee1bb76e7391b93eb12"));
	}

}
