package com.hawkprime.syndicate.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Feed Configuration.
 */
@Entity
@DiscriminatorValue("Feed")
public class FeedConfig extends Config {

	@ManyToOne
	@JoinColumn(name="entity_id")
	private Feed feed;
}
