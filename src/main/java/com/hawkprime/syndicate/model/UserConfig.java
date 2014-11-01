package com.hawkprime.syndicate.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Feed Configuration.
 */
@Entity
@DiscriminatorValue("User")
public class UserConfig extends Config {

	@ManyToOne
	@JoinColumn(name="entity_id")
	private User user;
}
