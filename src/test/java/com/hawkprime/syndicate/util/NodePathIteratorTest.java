package com.hawkprime.syndicate.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Node Path Iterator Tests.
 */
public class NodePathIteratorTest {

	@Test
	public void rootTest() {
		final NodePathIterator itr = new NodePathIterator(NodePath.root());
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(NodePath.root()));
		assertThat(itr.hasNext(), is(false));
	}

	@Test
	public void longTest() {
		final NodePathIterator itr = new NodePathIterator(NodePath.at("/foo/bar/Node/Section 3/Blue"));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(NodePath.root()));

		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(NodePath.at("/foo")));

		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(NodePath.at("/foo/bar")));

		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(NodePath.at("/foo/bar/Node")));

		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(NodePath.at("/foo/bar/Node/Section 3")));

		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(NodePath.at("/foo/bar/Node/Section 3/Blue")));

		assertThat(itr.hasNext(), is(false));
	}

	@Test(expected=UnsupportedOperationException.class)
	public void removeTest() {
		final NodePathIterator itr = new NodePathIterator(NodePath.root());
		itr.remove();
	}
}
