package com.hawkprime.syndicate.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * Post Entity.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable=false)
	private String title;

	@Column(nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime published;

	@Column(nullable=false)
	private String text;

	@Column(nullable=false)
	private String link;

	@Column(nullable=false)
	private String guid;

	@Column(nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime fetched;

	@ManyToOne
	@JoinColumn(name="feed_id", nullable=false)
	private Feed feed;

	@OneToMany(mappedBy="post", cascade={ CascadeType.DETACH, CascadeType.REMOVE })
	private List<State> states;

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
	 * @param id the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Gets the published.
	 *
	 * @return the published
	 */
	public LocalDateTime getPublished() {
		return published;
	}

	/**
	 * Sets the published.
	 *
	 * @param published the published to set
	 */
	public void setPublished(final LocalDateTime published) {
		this.published = published;
	}

	/**
	 * Gets the fetched.
	 *
	 * @return the fetched
	 */
	public LocalDateTime getFetched() {
		return fetched;
	}

	/**
	 * Sets the fetched.
	 *
	 * @param fetched the fetched to set
	 */
	public void setFetched(final LocalDateTime fetched) {
		this.fetched = fetched;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the text to set
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the link.
	 *
	 * @param link the link to set
	 */
	public void setLink(final String link) {
		this.link = link;
	}

	/**
	 * Gets the guid.
	 *
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Sets the GUID.
	 *
	 * @param guid the GUID to set
	 */
	public void setGuid(final String guid) {
		this.guid = guid;
	}

	/**
	 * Gets the feed.
	 *
	 * @return the feed
	 */
	public Feed getFeed() {
		return feed;
	}

	/**
	 * Sets the feed.
	 *
	 * @param feed the feed to set
	 */
	public void setFeed(final Feed feed) {
		this.feed = feed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("title", title)
				.toString();
	}
}
