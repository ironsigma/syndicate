package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * Feed Update Entity.
 */
@Entity
@Table(name="feed_update")
public class Update {
	@Id
	@GeneratedValue
	@Column(name="feed_update_id")
	private Long id;

	@Column(name="total_count", nullable=false)
	private Long totalCount;

	@Column(name="new_count", nullable=false)
	private Long newCount;

	@Column(nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime updated;

	@ManyToOne
	@JoinColumn(name="feed_id", nullable=false)
	private Feed feed;

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
	 * @return the totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(final Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the newCount
	 */
	public Long getNewCount() {
		return newCount;
	}

	/**
	 * @param newCount the newCount to set
	 */
	public void setNewCount(final Long newCount) {
		this.newCount = newCount;
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

	/**
	 * @return the updated
	 */
	public LocalDateTime getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(final LocalDateTime updated) {
		this.updated = updated;
	}
}
