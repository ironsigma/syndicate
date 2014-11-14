package com.hawkprime.syndicate.util;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Node Path.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public final class NodePath implements Iterable<NodePath> {
	/** Path Separator. */
	public static final String SEPARATOR = "/";

	private static final NodePath NODE_PATH_AT_ROOT = new NodePath(SEPARATOR);

	private final String path;
	private final int length;

	/**
	 * Instantiates a new node path.
	 *
	 * @param path the path
	 */
	private NodePath(final String path) {
		String normalized = path.replaceAll(SEPARATOR + "+", SEPARATOR);
		if (!path.startsWith(SEPARATOR)) {
			normalized = SEPARATOR + normalized;
		}
		if (!SEPARATOR.equals(normalized) && normalized.endsWith(SEPARATOR)) {
			this.path = normalized.substring(0, normalized.length() - 1);
		} else {
			this.path = normalized;
		}
		if (this.path.equals(SEPARATOR)) {
			length = 1;
		} else {
			length = StringUtils.countMatches(this.path, SEPARATOR) + 1;
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
		if (SEPARATOR.equals(appendPath)) {
			return this;
		}

		final String suffix;
		if (appendPath.endsWith(SEPARATOR)) {
			suffix = appendPath.substring(0, appendPath.length() - 1);
		} else {
			suffix = appendPath;
		}

		final String newPath;
		if (SEPARATOR.equals(path)) {
			if (suffix.startsWith(SEPARATOR)) {
				newPath = suffix;
			} else {
				newPath = path + suffix;
			}
		} else {
			if (suffix.startsWith(SEPARATOR)) {
				newPath = path + suffix;
			} else {
				newPath = path + SEPARATOR + suffix;
			}
		}

		return new NodePath(newPath);
	}

	/**
	 * Gets the last component.
	 *
	 * @return the last component
	 */
	public String getLastComponent() {
		return path.substring(path.lastIndexOf(SEPARATOR) + 1);
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public NodePath getParent() {
		if (SEPARATOR.equals(path)) {
			return null;
		}
		return new NodePath(path.substring(0, path.lastIndexOf(SEPARATOR) + 1));
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
