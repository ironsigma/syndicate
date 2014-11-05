package com.hawkprime.syndicate.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class Node.
 */
@Entity
@Table(name="node")
public class Node {

	@Id
	@GeneratedValue
	@Column(name="node_id")
	private Long id;

	@Column(nullable=false)
	private String path;

	@ManyToOne
	@JoinColumn(name="node_id")
	private Node parent;

	@OneToMany(mappedBy="parent")
	private List<Node> children;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

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

	/**
	 * Sets the children.
	 *
	 * @param children the new children
	 */
	public void setChildren(final List<Node> children) {
		this.children = children;
	}
}