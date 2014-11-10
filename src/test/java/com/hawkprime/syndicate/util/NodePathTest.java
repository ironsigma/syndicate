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
	public void pathDifferenceTest() {
		assertThat(NodePath.root()
				.getPathDifferences(NodePath.root()),
				is(nullValue()));

		assertThat(NodePath.at("/foo")
				.getPathDifferences(NodePath.root()),
				is(NodePath.at("/foo")));

		assertThat(NodePath.root()
				.getPathDifferences(NodePath.at("/foo")),
				is(NodePath.at("/foo")));

		assertThat(NodePath.at("/foo/bar")
				.getPathDifferences(NodePath.at("/foo")),
				is(NodePath.at("/bar")));

		assertThat(NodePath.at("/foo")
				.getPathDifferences(NodePath.at("/foo/bar")),
				is(NodePath.at("/bar")));

		assertThat(NodePath.at("/foox/bar/bin")
				.getPathDifferences(NodePath.at("/foo/bar/bin")),
				is(NodePath.at("/foox/bar/bin")));

		assertThat(NodePath.at("/foo/bar/bin")
				.getPathDifferences(NodePath.at("/foox/bar/bin")),
				is(NodePath.at("/foo/bar/bin")));

		assertThat(NodePath.at("/foo/bar/bin")
				.getPathDifferences(NodePath.at("/foo/barx/bin")),
				is(NodePath.at("/bar/bin")));

		assertThat(NodePath.at("/foo/barx/bin")
				.getPathDifferences(NodePath.at("/foo/bar/bin")),
				is(NodePath.at("/barx/bin")));

		assertThat(NodePath.at("/foo/bar/binx")
				.getPathDifferences(NodePath.at("/foo/bar/bin")),
				is(NodePath.at("/binx")));

		assertThat(NodePath.at("/foo/bar/bin")
				.getPathDifferences(NodePath.at("/foo/bar/binx")),
				is(NodePath.at("/bin")));

		assertThat(NodePath.at("/foo/bar/bin/dat")
				.getPathDifferences(NodePath.at("/foo/xbar/bin")),
				is(NodePath.at("/bar/bin/dat")));

		assertThat(NodePath.at("/foo/xbar/bin/dat")
				.getPathDifferences(NodePath.at("/foo/bar/bin")),
				is(NodePath.at("/xbar/bin/dat")));

		assertThat(NodePath.at("/foo/bar/bin")
				.getPathDifferences(NodePath.at("/foo/xbar/bin/dat")),
				is(NodePath.at("/xbar/bin/dat")));

		assertThat(NodePath.at("/foo/xbar/bin")
				.getPathDifferences(NodePath.at("/foo/bar/bin/dat")),
				is(NodePath.at("/bar/bin/dat")));
	}

	@Test
	public void commonPathTest() {
		assertThat(NodePath.root()
				.getCommonPath(NodePath.root()),
				is(NodePath.root()));

		assertThat(NodePath.at("/foo")
				.getCommonPath(NodePath.root()),
				is(NodePath.root()));

		assertThat(NodePath.root()
				.getCommonPath(NodePath.at("/foo")),
				is(NodePath.root()));

		assertThat(NodePath.at("/foo/bar")
				.getCommonPath(NodePath.at("/foo")),
				is(NodePath.at("/foo")));

		assertThat(NodePath.at("/foo")
				.getCommonPath(NodePath.at("/foo/bar")),
				is(NodePath.at("/foo")));

		assertThat(NodePath.at("/foox/bar/bin")
				.getCommonPath(NodePath.at("/foo/bar/bin")),
				is(NodePath.at("/")));

		assertThat(NodePath.at("/foo/bar/bin")
				.getCommonPath(NodePath.at("/foox/bar/bin")),
				is(NodePath.at("/")));

		assertThat(NodePath.at("/foo/bar/bin")
				.getCommonPath(NodePath.at("/foo/barx/bin")),
				is(NodePath.at("/foo")));

		assertThat(NodePath.at("/foo/barx/bin")
				.getCommonPath(NodePath.at("/foo/bar/bin")),
				is(NodePath.at("/foo")));

		assertThat(NodePath.at("/foo/bar/binx")
				.getCommonPath(NodePath.at("/foo/bar/bin")),
				is(NodePath.at("/foo/bar")));

		assertThat(NodePath.at("/foo/bar/bin")
				.getCommonPath(NodePath.at("/foo/bar/binx")),
				is(NodePath.at("/foo/bar")));
	}

	@Test
	public void dupSlashesTest() {
		final NodePath path = NodePath.at("//foo///bar/f/x//");
		assertThat(path.toString(), is("/foo/bar/f/x"));
	}

	@Test
	public void dupSlashesAppendTest() {
		NodePath root = NodePath.root();
		NodePath path = root.append("//foo//bar////f/x//");
		assertThat(path.toString(), is("/foo/bar/f/x"));

		root = NodePath.at("/foo/bar");
		path = root.append("//foo//bar////f/x///");
		assertThat(path.toString(), is("/foo/bar/foo/bar/f/x"));
	}

	@Test
	public void testCopy() {
		final NodePath aPath = NodePath.at("/foo");
		final NodePath copyPath = (NodePath) aPath.clone();
		assertThat(aPath, is(copyPath));
	}

	@Test
	public void rootNodeTest() {
		final NodePath path = NodePath.root();
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

		path = NodePath.root();
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
		NodePath newPath = path.append(NodePath.root());
		assertThat(newPath.toString(), is("/foo"));

		path = NodePath.at("/foo/bar");
		newPath = path.append(NodePath.root());
		assertThat(newPath.toString(), is("/foo/bar"));

		path = NodePath.root();
		newPath = path.append(NodePath.root());
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
		NodePath path = NodePath.root();
		assertThat(path.getLastComponent(), is(""));

		path = NodePath.at("/foo");
		assertThat(path.getLastComponent(), is("foo"));

		path = NodePath.at("/foo/bar/bin");
		assertThat(path.getLastComponent(), is("bin"));
	}

	@Test
	public void getParent() {
		NodePath path = NodePath.root();
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
		final NodePath n = NodePath.root();
		assertThat(n.equals(n), is(true));
		assertThat(NodePath.at("/foo/bar").equals(null), is(false));
		assertThat(NodePath.at("/foo/bar").equals("/foo/bar"), is(false));
		assertThat(NodePath.at("/foo/bar").equals(NodePath.at("/foo/bar")), is(true));
		assertThat(NodePath.at("/foo").equals(NodePath.at("/foo/bar")), is(false));
	}

	@Test
	public void hashTest() {
		assertThat(NodePath.root().hashCode(), is(NodePath.root().hashCode()));
		assertThat(NodePath.at("/foo/bar").hashCode(), is(NodePath.at("/foo/bar").hashCode()));
		assertThat(NodePath.root().hashCode(), is(not(NodePath.at("/foo/bar").hashCode())));
	}

	@Test
	public void toStringTest() {
		assertThat(NodePath.at("/foo/bar").toString(), is("/foo/bar"));
	}
}
