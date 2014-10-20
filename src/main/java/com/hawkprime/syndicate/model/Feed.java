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
}
