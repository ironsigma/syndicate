package com.hawkprime.syndicate.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The Class Node.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Entity
@Table(name="node")
public class Node extends AbstractEntity {
	@Column(nullable=false)
	private String path;

	@ManyToOne
	@JoinColumn(name="parent_id")
	private Node parent;

	@OneToMany(mappedBy="parent")
	private List<Node> children;

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(final String path) {
		this.path = path;
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(final Node parent) {
		this.parent = parent;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<Node> getChildren() {
		return children;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null || !(object instanceof Node)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final Node rhs = (Node) object;
		return new EqualsBuilder()
				.append(getId(), rhs.getId())
				.append(path, rhs.path)
				.append(parent, rhs.parent)
				.append(children, rhs.children)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.append(path)
				.append(parent)
				.append(children)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", getId())
				.append("path", path)
				.append("parent", parent)
				.append("children", children)
				.toString();
	}
}
