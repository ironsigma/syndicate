package com.hawkprime.syndicate.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Node Path.
 */
public class NodePath {
	private String path;

	public NodePath(final String path) {
		setPath(path);
	}

	public void setPath(final String path) {
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException("Path must start with slash");
		}
		if (!"/".equals(path) && path.endsWith("/")) {
			this.path = path.substring(0, path.length() - 1);
		} else {
			this.path = path;
		}
	}

	public NodePath append(final NodePath path) {
		append(path.path);
		return this;
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
				path = suffix;
			} else {
				path += suffix;
			}
		} else {
			if (suffix.startsWith("/")) {
				path += suffix;
			} else {
				path += "/" + suffix;
			}
		}
		return this;
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
