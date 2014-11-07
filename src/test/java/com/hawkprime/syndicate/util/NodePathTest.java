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
		NodePath.at("Foo");
	}

	@Test
	public void testCopy() {
		final NodePath aPath = NodePath.at("/foo");
		final NodePath copyPath = (NodePath) aPath.clone();
		assertThat(aPath, is(copyPath));
	}

	@Test
	public void rootNodeTest() {
		final NodePath path = NodePath.at("/");
		assertThat(path.toString(), is("/"));
	}

	@Test
	public void noEndingSlashTest() {
		final NodePath path = NodePath.at("/foo");
		assertThat(path.toString(), is("/foo"));
	}

	@Test
	public void endingSlashTest() {
		final NodePath path = NodePath.at("/foo/");
		assertThat(path.toString(), is("/foo"));
	}

	@Test
	public void appendTest() {
		NodePath path = NodePath.at("/foo");
		NodePath newPath = path.append("/");
		assertThat(newPath.toString(), is("/foo"));

		path = NodePath.at("/foo/bar");
		newPath = path.append("/");
		assertThat(newPath.toString(), is("/foo/bar"));

		path = NodePath.at("/");
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

		path = NodePath.at("/fi/fum");
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
		NodePath path = NodePath.at("/foo");
		NodePath newPath = path.append(NodePath.at("/"));
		assertThat(newPath.toString(), is("/foo"));

		path = NodePath.at("/foo/bar");
		newPath = path.append(NodePath.at("/"));
		assertThat(newPath.toString(), is("/foo/bar"));

		path = NodePath.at("/");
		newPath = path.append(NodePath.at("/"));
		assertThat(newPath.toString(), is("/"));

		newPath = path.append(NodePath.at("/foo"));
		assertThat(newPath.toString(), is("/foo"));

		newPath = path.append(NodePath.at("/foo/"));
		assertThat(newPath.toString(), is("/foo"));

		newPath = path.append(NodePath.at("/foo/bar"));
		assertThat(newPath.toString(), is("/foo/bar"));

		newPath = path.append(NodePath.at("/foo/bar/"));
		assertThat(newPath.toString(), is("/foo/bar"));

		path = NodePath.at("/fi/fum");
		newPath = path.append(NodePath.at("/foo/"));
		assertThat(newPath.toString(), is("/fi/fum/foo"));

		newPath = path.append(NodePath.at("/foo/bar"));
		assertThat(newPath.toString(), is("/fi/fum/foo/bar"));

		newPath = path.append(NodePath.at("/foo/bar/"));
		assertThat(newPath.toString(), is("/fi/fum/foo/bar"));
	}

	@Test
	public void getNodeTest() {
		NodePath path = NodePath.at("/");
		assertThat(path.getNode(), is(""));

		path = NodePath.at("/foo");
		assertThat(path.getNode(), is("foo"));

		path = NodePath.at("/foo/bar/bin");
		assertThat(path.getNode(), is("bin"));
	}

	@Test
	public void getParent() {
		NodePath path = NodePath.at("/");
		NodePath parent = path.getParent();
		assertThat(parent, is(nullValue()));

		path = NodePath.at("/foo");
		parent = path.getParent();
		assertThat(parent.toString(), is("/"));

		path = NodePath.at("/foo/bar");
		parent = path.getParent();
		assertThat(parent.toString(), is("/foo"));

		path = NodePath.at("/foo/bar/bin/get");
		parent = path.getParent();
		assertThat(parent.toString(), is("/foo/bar/bin"));
	}

	@Test
	public void equalsTest() {
		final NodePath n = NodePath.at("/");
		assertThat(n.equals(n), is(true));
		assertThat(NodePath.at("/foo/bar").equals(null), is(false));
		assertThat(NodePath.at("/foo/bar").equals("/foo/bar"), is(false));
		assertThat(NodePath.at("/foo/bar").equals(NodePath.at("/foo/bar")), is(true));
		assertThat(NodePath.at("/foo").equals(NodePath.at("/foo/bar")), is(false));
	}

	@Test
	public void hashTest() {
		assertThat(NodePath.at("/").hashCode(), is(NodePath.at("/").hashCode()));
		assertThat(NodePath.at("/foo/bar").hashCode(), is(NodePath.at("/foo/bar").hashCode()));
		assertThat(NodePath.at("/").hashCode(), is(not(NodePath.at("/foo/bar").hashCode())));
	}

	@Test
	public void toStringTest() {
		assertThat(NodePath.at("/foo/bar").toString(), is("/foo/bar"));
	}
}
