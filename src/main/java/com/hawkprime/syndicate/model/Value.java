package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
}
