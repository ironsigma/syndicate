package com.hawkprime.syndicate.util;

import java.util.Iterator;

/**
 * Node Path Iterator.
 */
public class NodePathIterator implements Iterator<NodePath> {
	private String[] pathComponents;
	private int next;

	public NodePathIterator(final NodePath path) {
		pathComponents = path.toString().split("/");
		if (pathComponents.length == 0) {
			pathComponents = new String[] {"/"};
		} else {
			pathComponents[0] = "/";
		}
	}

	@Override
	public boolean hasNext() {
		if (next >= pathComponents.length) {
			return false;
		}
		return true;
	}

	@Override
	public NodePath next() {
		next++;
		return NodePath.at(pathUpTo(next));
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private String pathUpTo(final int end) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < end; i++) {
			sb.append("/");
			sb.append(pathComponents[i]);
		}
		return sb.toString();
	}

}
