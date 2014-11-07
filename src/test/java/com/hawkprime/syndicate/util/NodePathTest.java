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
	public void testCopy() {
		final NodePath aPath = new NodePath("/foo");
		final NodePath copyPath = (NodePath) aPath.clone();
		assertThat(aPath, is(copyPath));
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
		NodePath path = new NodePath("/foo");
		NodePath newPath = path.append("/");
		assertThat(newPath.toString(), is("/foo"));

		path = new NodePath("/foo/bar");
		newPath = path.append("/");
		assertThat(newPath.toString(), is("/foo/bar"));

		path = new NodePath("/");
		newPath = path.append("/");
		assertThat(newPath.toString(), is("/"));

		newPath = path.append("foo");
		assertThat(newPath.toString(), is("/foo"));

		newPath = path.append("/foo");
		assertThat(newPath.toString(), is("/foo"));

		newPath = path.append("foo/");
		assertThat(newPath.toString(), is("/foo"));

		newPath = path.append("/foo/");
		assertThat(newPath.toString(), is("/foo"));

		newPath = path.append("foo/bar");
		assertThat(newPath.toString(), is("/foo/bar"));

		newPath = path.append("/foo/bar");
		assertThat(newPath.toString(), is("/foo/bar"));

		newPath = path.append("foo/bar/");
		assertThat(newPath.toString(), is("/foo/bar"));

		newPath = path.append("/foo/bar/");
		assertThat(newPath.toString(), is("/foo/bar"));

		path = new NodePath("/fi/fum");
		newPath = path.append("foo");
		assertThat(newPath.toString(), is("/fi/fum/foo"));

		newPath = path.append("foo");
		assertThat(newPath.toString(), is("/fi/fum/foo"));

		newPath = path.append("foo/");
		assertThat(newPath.toString(), is("/fi/fum/foo"));

		newPath = path.append("/foo/");
		assertThat(newPath.toString(), is("/fi/fum/foo"));

		newPath = path.append("foo/bar");
		assertThat(newPath.toString(), is("/fi/fum/foo/bar"));

		newPath = path.append("/foo/bar");
		assertThat(newPath.toString(), is("/fi/fum/foo/bar"));

		newPath = path.append("foo/bar/");
		assertThat(newPath.toString(), is("/fi/fum/foo/bar"));

		newPath = path.append("/foo/bar/");
		assertThat(newPath.toString(), is("/fi/fum/foo/bar"));
	}

	@Test
	public void appendNodeTest() {
		NodePath path = new NodePath("/foo");
		NodePath newPath = path.append(new NodePath("/"));
		assertThat(newPath.toString(), is("/foo"));

		path = new NodePath("/foo/bar");
		newPath = path.append(new NodePath("/"));
		assertThat(newPath.toString(), is("/foo/bar"));

		path = new NodePath("/");
		newPath = path.append(new NodePath("/"));
		assertThat(newPath.toString(), is("/"));

		newPath = path.append(new NodePath("/foo"));
		assertThat(newPath.toString(), is("/foo"));

		newPath = path.append(new NodePath("/foo/"));
		assertThat(newPath.toString(), is("/foo"));

		newPath = path.append(new NodePath("/foo/bar"));
		assertThat(newPath.toString(), is("/foo/bar"));

		newPath = path.append(new NodePath("/foo/bar/"));
		assertThat(newPath.toString(), is("/foo/bar"));

		path = new NodePath("/fi/fum");
		newPath = path.append(new NodePath("/foo/"));
		assertThat(newPath.toString(), is("/fi/fum/foo"));

		newPath = path.append(new NodePath("/foo/bar"));
		assertThat(newPath.toString(), is("/fi/fum/foo/bar"));

		newPath = path.append(new NodePath("/foo/bar/"));
		assertThat(newPath.toString(), is("/fi/fum/foo/bar"));
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
