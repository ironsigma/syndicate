CREATE TABLE feed (
	feed_id INTEGER IDENTITY,
	name VARCHAR(255) NOT NULL,
	url VARCHAR(255) NOT NULL,
);

CREATE TABLE post (
	post_id INTEGER IDENTITY,
	title VARCHAR(255) NOT NULL,
	published TIMESTAMP WITH TIME ZONE NOT NULL,
	text VARCHAR(255) NOT NULL,
	link VARCHAR(255) NOT NULL,
	guid VARCHAR(40) NOT NULL,
	feed_id INTEGER NOT NULL,
	FOREIGN KEY (feed_id) REFERENCES feed (feed_id),
	CONSTRAINT uc_post_guid UNIQUE (guid),
);

CREATE TABLE feed_update (
	feed_update_id INTEGER IDENTITY,
	total_count INTEGER NOT NULL,
	new_count INTEGER NOT NULL,
	updated TIMESTAMP WITH TIME ZONE NOT NULL,
	feed_id INTEGER NOT NULL,
	FOREIGN KEY (feed_id) REFERENCES feed (feed_id),
);
