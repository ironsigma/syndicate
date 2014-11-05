package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * State Entity.
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
	 * @return the read
	 */
	public Boolean isRead() {
		return read;
	}

	/**
	 * @param read the read to set
	 */
	public void setRead(final Boolean read) {
		this.read = read;
	}

	/**
	 * @return the stared
	 */
	public Boolean isStared() {
		return stared;
	}

	/**
	 * @param stared the stared to set
	 */
	public void setStared(final Boolean stared) {
		this.stared = stared;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * @return the post
	 */
	public Post getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(final Post post) {
		this.post = post;
	}
}
