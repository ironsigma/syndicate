package com.hawkprime.syndicate.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private Node parent;

	@OneToMany(mappedBy="parent")
	private List<Node> children;
}
