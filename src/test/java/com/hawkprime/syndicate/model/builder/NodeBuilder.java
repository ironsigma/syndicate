package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Node;

/**
 * Node Builder.
 */
public class NodeBuilder {
	private Long id;
	private Node parent;
	private String path;

	/**
	 * Builds the node.
	 *
	 * @return the node
	 */
	public Node build() {
		Node node = new Node();
		node.setId(id);
		node.setParent(parent);
		node.setPath(path);
		return node;
	}

	/**
	 * With id.
	 *
	 * @param id the id
	 * @return the node builder
	 */
	public NodeBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	/**
	 * With parent.
	 * @param parent the parent
	 * @return the node builder
	 */
	public NodeBuilder withParent(final Node parent) {
		this.parent = parent;
		return this;
	}

	/**
	 * With path.
	 *
	 * @param path the path
	 * @return the node builder
	 */
	public NodeBuilder withPath(final String path) {
		this.path = path;
		return this;
	}
}
