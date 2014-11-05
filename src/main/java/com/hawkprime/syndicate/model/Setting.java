package com.hawkprime.syndicate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="setting")
public class Setting {

	@Id
	@GeneratedValue
	@Column(name="setting_id")
	private Long id;

	@Column(nullable=false)
	private String name;

}
