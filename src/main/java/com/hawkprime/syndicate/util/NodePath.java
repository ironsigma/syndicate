package com.hawkprime.syndicate.util;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Node Path.
 */
public final class NodePath implements Iterable<NodePath> {
	private static final NodePath NODE_PATH_AT_ROOT = new NodePath("/");

	private final String path;
	private final int length;

	/**
	 * Instantiates a new node path.
	 *
	 * @param path the path
	 */
	private NodePath(final String path) {
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException("Path must start with slash");
		}
		final String normalized = path.replaceAll("/+", "/");
		if (!"/".equals(normalized) && normalized.endsWith("/")) {
			this.path = normalized.substring(0, normalized.length() - 1);
		} else {
			this.path = normalized;
		}
		if (this.path.equals("/")) {
			length = 1;
		} else {
			length = StringUtils.countMatches(this.path, "/") + 1;
		}
	}

	/**
	 * Create a new NodePath at specified path.
	 *
	 * @param path the path
	 * @return the node path
	 */
	public static NodePath at(final String path) {
		return new NodePath(path);
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Root instance.
	 *
	 * @return the node path
	 */
	public static NodePath root() {
		return NODE_PATH_AT_ROOT;
	}

	/**
	 * Gets the path differences.
	 *
	 * @param otherNode the other node
	 * @return the path differences
	 */
	public NodePath getPathDifferences(final NodePath otherNode) {
		final NodePathIterator shortNodePathIterator;
		final NodePathIterator longNodePathIterator;
		if (this.getLength() < otherNode.getLength()) {
			shortNodePathIterator = new NodePathIterator(this);
			longNodePathIterator = new NodePathIterator(otherNode);
		} else {
			shortNodePathIterator = new NodePathIterator(otherNode);
			longNodePathIterator = new NodePathIterator(this);
		}
		NodePath currentPath;
		NodePath pathDifference = NodePath.root();
		while (longNodePathIterator.hasNext()) {
			currentPath = longNodePathIterator.next();
			if (!shortNodePathIterator.hasNext()) {
				pathDifference = pathDifference.append(currentPath.getLastComponent());
				continue;
			}
			if (!currentPath.equals(shortNodePathIterator.next())) {
				pathDifference = pathDifference.append(currentPath.getLastComponent());
			}
		}
		if (pathDifference.equals(NodePath.root())) {
			return null;
		}
		return pathDifference;
	}

	/**
	 * Gets the common path.
	 *
	 * @param otherNode the other node
	 * @return the common path
	 */
	public NodePath getCommonPath(final NodePath otherNode) {
		final NodePathIterator shortNodePathIterator;
		final NodePathIterator longNodePathIterator;
		if (path.length() < otherNode.path.length()) {
			shortNodePathIterator = new NodePathIterator(this);
			longNodePathIterator = new NodePathIterator(otherNode);
		} else {
			shortNodePathIterator = new NodePathIterator(otherNode);
			longNodePathIterator = new NodePathIterator(this);
		}
		NodePath currentPath;
		NodePath oldPath = NodePath.root();
		while (shortNodePathIterator.hasNext()) {
			currentPath = shortNodePathIterator.next();
			if (!currentPath.equals(longNodePathIterator.next())) {
				return oldPath;
			}
			oldPath = currentPath;
		}
		return oldPath;
	}

	/**
	 * Append.
	 *
	 * @param path the path
	 * @return the node path
	 */
	public NodePath append(final NodePath path) {
		return append(path.path);
	}

	/**
	 * Append.
	 *
	 * @param appendPath the append path
	 * @return the node path
	 */
	public NodePath append(final String appendPath) {
		if ("/".equals(appendPath)) {
			return this;
		}
		String suffix;
		if (appendPath.endsWith("/")) {
			suffix = appendPath.substring(0, appendPath.length() - 1);
		} else {
			suffix = appendPath;
		}
		if ("/".equals(path)) {
			if (suffix.startsWith("/")) {
				return new NodePath(suffix);
			} else {
				return new NodePath(path + suffix);
			}
		} else {
			if (suffix.startsWith("/")) {
				return new NodePath(path + suffix);
			} else {
				return new NodePath(path + "/" + suffix);
			}
		}
	}

	/**
	 * Gets the last component.
	 *
	 * @return the last component
	 */
	public String getLastComponent() {
		return path.substring(path.lastIndexOf('/') + 1);
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public NodePath getParent() {
		if ("/".equals(path)) {
			return null;
		}
		return new NodePath(path.substring(0, path.lastIndexOf('/') + 1));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (object == null || !(object instanceof NodePath)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final NodePath rhs = (NodePath) object;
		return new EqualsBuilder()
				.append(path, rhs.path)
				.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(path)
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return path;
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<NodePath> iterator() {
		return new NodePathIterator(this);
	}
}
