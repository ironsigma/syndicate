package com.hawkprime.syndicate.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Node Path Iterator Tests.
 */
public class NodePathIteratorTest {

	/**
	 * Root test.
	 */
	@Test
	public void rootTest() {
		NodePathIterator itr = new NodePathIterator(NodePath.root());
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(NodePath.root()));
		assertThat(itr.hasNext(), is(false));
	}

	/**
	 * Long test.
	 */
	@Test
	public void longTest() {
		NodePath path = NodePath.at("/foo/bar/Node/Section 3/Blue");
		NodePathIterator itr = new NodePathIterator(path);
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
		assertThat(itr.next(), is(path));

		assertThat(itr.hasNext(), is(false));
	}

	/**
	 * Removes the test.
	 */
	@Test(expected=UnsupportedOperationException.class)
	public void removeTest() {
		NodePathIterator itr = new NodePathIterator(NodePath.root());
		itr.remove();
	}
}
