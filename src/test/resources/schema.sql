CREATE TABLE user (
	user_id INTEGER IDENTITY,
	name VARCHAR(128) NOT NULL,
);

CREATE TABLE feed (
	feed_id INTEGER IDENTITY,
	name VARCHAR(255) NOT NULL,
	url VARCHAR(2048) NOT NULL,
	update_frequency INTEGER NOT NULL,
	active BOOLEAN NOT NULL,
);

CREATE TABLE post (
	post_id INTEGER IDENTITY,
	title VARCHAR(512) NOT NULL,
	published TIMESTAMP WITH TIME ZONE NOT NULL,
	fetched TIMESTAMP WITH TIME ZONE NOT NULL,
	text VARCHAR(1000000) NOT NULL,
	link VARCHAR(2048) NOT NULL,
	guid VARCHAR(40) NOT NULL,
	feed_id INTEGER NOT NULL,
	FOREIGN KEY (feed_id) REFERENCES feed (feed_id),
	CONSTRAINT uc_post_guid UNIQUE (guid),
);

CREATE INDEX post_guid_idx ON post (guid);

CREATE TABLE post_state (
	post_state_id INTEGER IDENTITY,
	read BOOLEAN NOT NULL,
	stared BOOLEAN NOT NULL,
	post_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	FOREIGN KEY (post_id) REFERENCES post (post_id),
	FOREIGN KEY (user_id) REFERENCES user (user_id),
);

CREATE TABLE feed_update (
	feed_update_id INTEGER IDENTITY,
	total_count INTEGER NOT NULL,
	new_count INTEGER NOT NULL,
	updated TIMESTAMP WITH TIME ZONE NOT NULL,
	feed_id INTEGER NOT NULL,
	FOREIGN KEY (feed_id) REFERENCES feed (feed_id),
);
