package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
	 * Gets the total count.
	 *
	 * @return the totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * Sets the total count.
	 *
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(final Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * Gets the new count.
	 *
	 * @return the newCount
	 */
	public Long getNewCount() {
		return newCount;
	}

	/**
	 * Sets the new count.
	 *
	 * @param newCount the newCount to set
	 */
	public void setNewCount(final Long newCount) {
		this.newCount = newCount;
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

	/**
	 * Gets the updated.
	 *
	 * @return the updated
	 */
	public LocalDateTime getUpdated() {
		return updated;
	}

	/**
	 * Sets the updated.
	 *
	 * @param updated the updated to set
	 */
	public void setUpdated(final LocalDateTime updated) {
		this.updated = updated;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (object == null || !(object instanceof Update)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final Update rhs = (Update) object;
		return new EqualsBuilder()
				.append(id, rhs.id)
				.append(totalCount, rhs.totalCount)
				.append(newCount, rhs.newCount)
				.append(updated, rhs.updated)
				.append(feed, rhs.feed)
				.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(totalCount)
				.append(newCount)
				.append(updated)
				.append(feed)
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("total", totalCount)
				.append("new", newCount)
				.append("date", updated)
				.append("feed", feed.getName())
				.toString();
	}
}
