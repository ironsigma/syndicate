package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * The Class Feed.
 */
@Entity
@Table(name="feed")
public class Feed {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String url;

	@Column(nullable=false)
	private Boolean active;

	@Column(nullable=false, name="update_frequency")
	private Integer updateFrequency;

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
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * Checks if is active.
	 *
	 * @return the boolean
	 */
	public Boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(final Boolean active) {
		this.active = active;
	}

	/**
	 * Gets the update frequency.
	 *
	 * @return the update frequency
	 */
	public Integer getUpdateFrequency() {
		return updateFrequency;
	}

	/**
	 * Sets the update frequency.
	 *
	 * @param updateFrequency the new update frequency
	 */
	public void setUpdateFrequency(final Integer updateFrequency) {
		this.updateFrequency = updateFrequency;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (object == null || !(object instanceof Feed)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final Feed rhs = (Feed) object;
		return new EqualsBuilder()
				.append(id, rhs.id)
				.append(name, rhs.name)
				.append(url, rhs.url)
				.append(active, rhs.active)
				.append(updateFrequency, rhs.updateFrequency)
				.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(name)
				.append(url)
				.append(active)
				.append(updateFrequency)
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("name", name)
				.toString();
	}
}
