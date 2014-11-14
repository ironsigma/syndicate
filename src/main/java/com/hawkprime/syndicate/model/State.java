package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * State Entity.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Entity
@Table(name="post_state")
public class State {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable=false)
	private Boolean read;

	@Column(nullable=false)
	private Boolean stared;

	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	@ManyToOne
	@JoinColumn(name="post_id", nullable=false)
	private Post post;

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
	 * Checks if is read.
	 *
	 * @return the read
	 */
	public Boolean isRead() {
		return read;
	}

	/**
	 * Sets the read.
	 *
	 * @param read the read to set
	 */
	public void setRead(final Boolean read) {
		this.read = read;
	}

	/**
	 * Checks if is stared.
	 *
	 * @return the stared
	 */
	public Boolean isStared() {
		return stared;
	}

	/**
	 * Sets the stared.
	 *
	 * @param stared the stared to set
	 */
	public void setStared(final Boolean stared) {
		this.stared = stared;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * Gets the post.
	 *
	 * @return the post
	 */
	public Post getPost() {
		return post;
	}

	/**
	 * Sets the post.
	 *
	 * @param post the post to set
	 */
	public void setPost(final Post post) {
		this.post = post;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("read", read)
				.append("stared", stared)
				.append("user", user.getName())
				.append("post", post.getTitle())
				.toString();
	}
}
