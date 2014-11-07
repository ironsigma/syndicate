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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return the published
	 */
	public LocalDateTime getPublished() {
		return published;
	}

	/**
	 * @param published the published to set
	 */
	public void setPublished(final LocalDateTime published) {
		this.published = published;
	}

	/**
	 * @return the fetched
	 */
	public LocalDateTime getFetched() {
		return fetched;
	}

	/**
	 * @param fetched the fetched to set
	 */
	public void setFetched(final LocalDateTime fetched) {
		this.fetched = fetched;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(final String link) {
		this.link = link;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(final String guid) {
		this.guid = guid;
	}

	/**
	 * @return the feed
	 */
	public Feed getFeed() {
		return feed;
	}

	/**
	 * @param feed the feed to set
	 */
	public void setFeed(final Feed feed) {
		this.feed = feed;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("title", title)
				.toString();
	}
}
