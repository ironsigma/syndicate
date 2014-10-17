package com.hawkprime.syndicate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="feed_update")
public class Update {
	@Id
	@GeneratedValue
	@Column(name="update_id")
	private Long id;
	
	@Column(name="total_count", nullable=false)
	private Long totalCount;

	@Column(name="new_count", nullable=false)
	private Long newCount;

	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	@ManyToOne
	@JoinColumn(name="feed_id", insertable=false, updatable=false, nullable=false)
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
	public void setId(Long id) {
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
	public void setTotalCount(Long totalCount) {
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
	public void setNewCount(Long newCount) {
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
	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}