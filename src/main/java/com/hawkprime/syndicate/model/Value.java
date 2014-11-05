package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Setting Value.
 */
@Entity
@Table(name="value")
public class Value {

	@Id
	@GeneratedValue
	@Column(name="value_id")
	private Long id;

	@Column(nullable=false)
	private String type;

	@Column(nullable=false)
	private String value;

	@ManyToOne
	@JoinColumn(name="node_id")
	private Node node;

	@ManyToOne
	@JoinColumn(name="setting_id")
	private Setting setting;

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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	public void setNode(final Node node) {
		this.node = node;
	}

	/**
	 * Gets the setting.
	 *
	 * @return the setting
	 */
	public Setting getSetting() {
		return setting;
	}

	/**
	 * Sets the setting.
	 *
	 * @param setting the new setting
	 */
	public void setSetting(final Setting setting) {
		this.setting = setting;
	}
}
