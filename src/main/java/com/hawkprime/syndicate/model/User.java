package com.hawkprime.syndicate.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User Entity.
 */
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable=false)
	private String name;

	@OneToMany(mappedBy="user", cascade={ CascadeType.DETACH, CascadeType.REMOVE })
	private List<State> states;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
}
