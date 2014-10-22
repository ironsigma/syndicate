package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Feed.
 */
@Entity
@Table(name="feed")
public class Feed {
	@Id
	@GeneratedValue
	@Column(name="feed_id")
	private Long id;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String url;

	@Column(nullable=false)
	private Boolean active;

	@Column(nullable=false, name="update_frequency")
	private Long updateFrequency;

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
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
	/**
	 * @return the active
	 */
	public Boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(final Boolean active) {
		this.active = active;
	}
	/**
	 * @return the updateFrequency
	 */
	public Long getUpdateFrequency() {
		return updateFrequency;
	}
	/**
	 * @param updateFrequency the updateFrequency to set
	 */
	public void setUpdateFrequency(final Long updateFrequency) {
		this.updateFrequency = updateFrequency;
	}
}
