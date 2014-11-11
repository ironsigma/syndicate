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

	public static NodePath at(final String path) {
		return new NodePath(path);
	}

	public int getLength() {
		return length;
	}

	public static NodePath root() {
		return NODE_PATH_AT_ROOT;
	}

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

	public NodePath append(final NodePath path) {
		return append(path.path);
	}

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

	public String getLastComponent() {
		return path.substring(path.lastIndexOf('/') + 1);
	}

	public NodePath getParent() {
		if ("/".equals(path)) {
			return null;
		}
		return new NodePath(path.substring(0, path.lastIndexOf('/') + 1));
	}

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

	@Override
	public Object clone() {
		return this;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(path)
				.toHashCode();
	}

	@Override
	public String toString() {
		return path;
	}

	@Override
	public Iterator<NodePath> iterator() {
		return new NodePathIterator(this);
	}
}
