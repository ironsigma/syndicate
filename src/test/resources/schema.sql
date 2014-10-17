CREATE TABLE feed (
	feed_id INTEGER IDENTITY,
	name VARCHAR(255),
	url VARCHAR(255),
);

CREATE TABLE post (
	post_id INTEGER IDENTITY,
	title VARCHAR(255),
	published TIMESTAMP WITH TIME ZONE,
	text VARCHAR(255),
	link VARCHAR(255),
	guid VARCHAR(40),
	feed_id INTEGER,
	FOREIGN KEY (feed_id) REFERENCES feed (feed_id),
);

CREATE TABLE feed_update (
	feed_update_id INTEGER IDENTITY,
	total_count INTEGER,
	new_count INTEGER,
	updated TIMESTAMP WITH TIME ZONE,
	feed_id INTEGER,
	FOREIGN KEY (feed_id) REFERENCES feed (feed_id),
);