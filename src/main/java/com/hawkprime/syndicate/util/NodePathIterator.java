package com.hawkprime.syndicate.util;

import java.util.Iterator;

/**
 * Node Path Iterator.
 */
public class NodePathIterator implements Iterator<NodePath> {
	private String[] pathComponents;
	private int next;

	/**
	 * Instantiates a new node path iterator.
	 *
	 * @param path the path
	 */
	public NodePathIterator(final NodePath path) {
		pathComponents = path.toString().split("/");
		if (pathComponents.length == 0) {
			pathComponents = new String[] {"/"};
		} else {
			pathComponents[0] = "/";
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (next >= pathComponents.length) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public NodePath next() {
		next++;
		return NodePath.at(pathUpTo(next));
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Path up to.
	 * Build path with components up to the specified index.
	 * @param end the end
	 * @return the string
	 */
	private String pathUpTo(final int end) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < end; i++) {
			sb.append("/");
			sb.append(pathComponents[i]);
		}
		return sb.toString();
	}

}
