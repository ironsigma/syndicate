-- Setting
CREATE TABLE setting (
	setting_id INTEGER IDENTITY,
	name VARCHAR(512),
);

-- Node
CREATE TABLE node (
	node_id INTEGER IDENTITY,
	parent_id INTEGER,
	path VARCHAR(2048),
	FOREIGN KEY (parent_id) REFERENCES node (node_id) ON DELETE CASCADE,
);

-- Value
CREATE TABLE value (
	value_id INTEGER IDENTITY,
	node_id INTEGER,
	setting_id INTEGER,
	type VARCHAR(64),
	value VARCHAR(4096),
	FOREIGN KEY (node_id) REFERENCES node (node_id) ON DELETE CASCADE,
	FOREIGN KEY (setting_id) REFERENCES setting (setting_id) ON DELETE CASCADE,
);

CREATE TABLE config (
	config_id INTEGER IDENTITY,
	section varchar(1024) NOT NULL,
	key VARCHAR(1024) NOT NULL,
	value_type VARCHAR(16) NOT NULL,
	string_value VARCHAR(4096),
	boolean_value BOOLEAN,
	numeric_value INTEGER,
	decimal_value DECIMAL,
	date_value TIMESTAMP WITH TIME ZONE,
	reference_id INTEGER,
	CONSTRAINT uc_config_value UNIQUE (section, key, value_type, reference_id),
);

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
	FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
);

CREATE TABLE feed_update (
	feed_update_id INTEGER IDENTITY,
	total_count INTEGER NOT NULL,
	new_count INTEGER NOT NULL,
	updated TIMESTAMP WITH TIME ZONE NOT NULL,
	feed_id INTEGER NOT NULL,
	FOREIGN KEY (feed_id) REFERENCES feed (feed_id),
);
