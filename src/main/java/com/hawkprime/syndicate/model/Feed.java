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
 * Feed.
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
	 * Get the Id.
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
	public Integer getUpdateFrequency() {
		return updateFrequency;
	}
	/**
	 * @param updateFrequency the updateFrequency to set
	 */
	public void setUpdateFrequency(final Integer updateFrequency) {
		this.updateFrequency = updateFrequency;
	}

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

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("name", name)
				.toString();
	}
}
