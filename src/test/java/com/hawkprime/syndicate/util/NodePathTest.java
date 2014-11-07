package com.hawkprime.syndicate.util;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Node Path Test.
 */
public class NodePathTest {

	@Test(expected=IllegalArgumentException.class)
	public void noSlashTest() {
		new NodePath("Foo");
	}

	@Test
	public void rootNodeTest() {
		final NodePath path = new NodePath("/");
		assertThat(path.toString(), is("/"));
	}

	@Test
	public void noEndingSlashTest() {
		final NodePath path = new NodePath("/foo");
		assertThat(path.toString(), is("/foo"));
	}

	@Test
	public void endingSlashTest() {
		final NodePath path = new NodePath("/foo/");
		assertThat(path.toString(), is("/foo"));
	}

	@Test
	public void appendTest() {
		NodePath path = new NodePath("/");
		path.append("/");
		assertThat(path.toString(), is("/"));

		path = new NodePath("/foo");
		path.append("/");
		assertThat(path.toString(), is("/foo"));

		path = new NodePath("/foo/bar");
		path.append("/");
		assertThat(path.toString(), is("/foo/bar"));

		path = new NodePath("/");
		path.append("foo");
		assertThat(path.toString(), is("/foo"));

		path = new NodePath("/");
		path.append("/foo");
		assertThat(path.toString(), is("/foo"));

		path = new NodePath("/");
		path.append("foo/");
		assertThat(path.toString(), is("/foo"));

		path = new NodePath("/");
		path.append("/foo/");
		assertThat(path.toString(), is("/foo"));

		path = new NodePath("/");
		path.append("foo/bar");
		assertThat(path.toString(), is("/foo/bar"));

		path = new NodePath("/");
		path.append("/foo/bar");
		assertThat(path.toString(), is("/foo/bar"));

		path = new NodePath("/");
		path.append("foo/bar/");
		assertThat(path.toString(), is("/foo/bar"));

		path = new NodePath("/");
		path.append("/foo/bar/");
		assertThat(path.toString(), is("/foo/bar"));

		path = new NodePath("/fi/fum");
		path.append("foo");
		assertThat(path.toString(), is("/fi/fum/foo"));

		path = new NodePath("/fi/fum");
		path.append("foo");
		assertThat(path.toString(), is("/fi/fum/foo"));

		path = new NodePath("/fi/fum");
		path.append("foo/");
		assertThat(path.toString(), is("/fi/fum/foo"));

		path = new NodePath("/fi/fum");
		path.append("/foo/");
		assertThat(path.toString(), is("/fi/fum/foo"));

		path = new NodePath("/fi/fum");
		path.append("foo/bar");
		assertThat(path.toString(), is("/fi/fum/foo/bar"));

		path = new NodePath("/fi/fum");
		path.append("/foo/bar");
		assertThat(path.toString(), is("/fi/fum/foo/bar"));

		path = new NodePath("/fi/fum");
		path.append("foo/bar/");
		assertThat(path.toString(), is("/fi/fum/foo/bar"));

		path = new NodePath("/fi/fum");
		path.append("/foo/bar/");
		assertThat(path.toString(), is("/fi/fum/foo/bar"));
	}

	@Test
	public void appendNodeTest() {
		NodePath path = new NodePath("/");
		path.append(new NodePath("/"));
		assertThat(path.toString(), is("/"));

		path = new NodePath("/foo");
		path.append(new NodePath("/"));
		assertThat(path.toString(), is("/foo"));

		path = new NodePath("/foo/bar");
		path.append(new NodePath("/"));
		assertThat(path.toString(), is("/foo/bar"));

		path = new NodePath("/");
		path.append(new NodePath("/foo"));
		assertThat(path.toString(), is("/foo"));

		path = new NodePath("/");
		path.append(new NodePath("/foo/"));
		assertThat(path.toString(), is("/foo"));

		path = new NodePath("/");
		path.append(new NodePath("/foo/bar"));
		assertThat(path.toString(), is("/foo/bar"));

		path = new NodePath("/");
		path.append(new NodePath("/foo/bar/"));
		assertThat(path.toString(), is("/foo/bar"));

		path = new NodePath("/fi/fum");
		path.append(new NodePath("/foo/"));
		assertThat(path.toString(), is("/fi/fum/foo"));

		path = new NodePath("/fi/fum");
		path.append(new NodePath("/foo/bar"));
		assertThat(path.toString(), is("/fi/fum/foo/bar"));

		path = new NodePath("/fi/fum");
		path.append(new NodePath("/foo/bar/"));
		assertThat(path.toString(), is("/fi/fum/foo/bar"));
	}

	@Test
	public void getNodeTest() {
		NodePath path = new NodePath("/");
		assertThat(path.getNode(), is(""));

		path = new NodePath("/foo");
		assertThat(path.getNode(), is("foo"));

		path = new NodePath("/foo/bar/bin");
		assertThat(path.getNode(), is("bin"));
	}

	@Test
	public void getParent() {
		NodePath path = new NodePath("/");
		NodePath parent = path.getParent();
		assertThat(parent, is(nullValue()));

		path = new NodePath("/foo");
		parent = path.getParent();
		assertThat(parent.toString(), is("/"));

		path = new NodePath("/foo/bar");
		parent = path.getParent();
		assertThat(parent.toString(), is("/foo"));

		path = new NodePath("/foo/bar/bin/get");
		parent = path.getParent();
		assertThat(parent.toString(), is("/foo/bar/bin"));
	}

	@Test
	public void equalsTest() {
		final NodePath n = new NodePath("/");
		assertThat(n.equals(n), is(true));
		assertThat(new NodePath("/foo/bar").equals(null), is(false));
		assertThat(new NodePath("/foo/bar").equals("/foo/bar"), is(false));
		assertThat(new NodePath("/foo/bar").equals(new NodePath("/foo/bar")), is(true));
		assertThat(new NodePath("/foo").equals(new NodePath("/foo/bar")), is(false));
	}

	@Test
	public void hashTest() {
		assertThat(new NodePath("/").hashCode(), is(new NodePath("/").hashCode()));
		assertThat(new NodePath("/foo/bar").hashCode(), is(new NodePath("/foo/bar").hashCode()));
		assertThat(new NodePath("/").hashCode(), is(not(new NodePath("/foo/bar").hashCode())));
	}

	@Test
	public void toStringTest() {
		assertThat(new NodePath("/foo/bar").toString(), is("/foo/bar"));
	}
}
