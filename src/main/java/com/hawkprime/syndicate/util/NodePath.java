package com.hawkprime.syndicate.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Node Path.
 */
public final class NodePath {
	private final String path;

	private NodePath(final String path) {
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException("Path must start with slash");
		}
		if (!"/".equals(path) && path.endsWith("/")) {
			this.path = path.substring(0, path.length() - 1);
		} else {
			this.path = path;
		}
	}

	public static NodePath at(final String path) {
		return new NodePath(path);
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

	public String getNode() {
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
}
