-- Setting
INSERT INTO setting (setting_id, name)
VALUES ('1', 'MaxUpdate');
INSERT INTO setting (setting_id, name)
VALUES ('2', 'MinUpdate');
INSERT INTO setting (setting_id, name)
VALUES ('3', 'MinOptimalRange');
INSERT INTO setting (setting_id, name)
VALUES ('4', 'MaxOptimalRange');
INSERT INTO setting (setting_id, name)
VALUES ('5', 'UpdateInterval');

-- Node
INSERT INTO node (node_id, parent_id, path)
VALUES ('1', NULL, '/');
INSERT INTO node (node_id, parent_id, path)
VALUES ('2', '1', '/App');
INSERT INTO node (node_id, parent_id, path)
VALUES ('3', '2', '/App/Feed');
INSERT INTO node (node_id, parent_id, path)
VALUES ('4', '3', '/App/Feed/1');
INSERT INTO node (node_id, parent_id, path)
VALUES ('5', '3', '/App/Feed/2');

-- Node-Setting
INSERT INTO value (node_id, setting_id, type, value)
VALUES ('3', '1', 'integer', '1');		-- MaxUpdate /App/feed
INSERT INTO value (node_id, setting_id, type, value)
VALUES ('3', '2', 'integer', '2880');	-- MinUpdate /App/feed
INSERT INTO value (node_id, setting_id, type, value)
VALUES ('3', '3', 'integer', '70');		-- MinOptimalRange /App/feed
INSERT INTO value (node_id, setting_id, type, value)
VALUES ('3', '4', 'integer', '80');		-- MaxOptimalRange /App/feed
INSERT INTO value (node_id, setting_id, type, value)
VALUES ('3', '5', 'integer', '15');		-- UpdateInterval /App/feed
INSERT INTO value (node_id, setting_id, type, value)
VALUES ('4', '5', 'integer', '60');		-- UpdateInterval /App/Feed/1

-- Config
INSERT INTO config (config_id, section, key, value_type, string_value, reference_id)
VALUES (1, 'User', 'name', 'STRING', 'Joe Hawk', 1);
INSERT INTO config (config_id, section, key, value_type, string_value, reference_id)
VALUES (2, 'User', 'name', 'STRING', 'Joe Hawk 2', null);
INSERT INTO config (config_id, section, key, value_type, string_value, reference_id)
VALUES (3, 'User', 'name', 'STRING', 'Joe Hawk 3', 2);
INSERT INTO config (config_id, section, key, value_type, numeric_value, reference_id)
VALUES (4, 'Feed', 'max-update', 'NUMERIC', 1, 1);
INSERT INTO config (config_id, section, key, value_type, numeric_value, reference_id)
VALUES (5, 'Feed', 'min-update', 'NUMERIC', 2880, 1);
INSERT INTO config (config_id, section, key, value_type, numeric_value, reference_id)
VALUES (6, 'Feed', 'max-optimal', 'NUMERIC', 80, 1);
INSERT INTO config (config_id, section, key, value_type, numeric_value, reference_id)
VALUES (7, 'Feed', 'min-optimal', 'NUMERIC', 70, 1);

-- Users
INSERT INTO user (user_id, name)
VALUES (1, 'Joe Hawk');
INSERT INTO user (user_id, name)
VALUES (2, 'Jane Hawk');

-- Feeds
INSERT INTO feed (feed_id, name, url, update_frequency, active)
VALUES (1, 'MyFeed', 'http://myfeed.com/rss', 60, 1);
INSERT INTO feed (feed_id, name, url, update_frequency, active)
VALUES (2, 'Another Feed', 'http://anotherfeed.com/rss', 30, 0);
INSERT INTO feed (feed_id, name, url, update_frequency, active)
VALUES (3, 'Your Feed', 'http://yourfeed.com/rss', 25, 1);

-- Posts
INSERT INTO post (post_id, title, published, fetched, link, guid, text, feed_id)
VALUES (1, 'My First Post', '2014-10-17 14:39:00', '2014-10-29 14:08:00',
		'http://myfeed.com/my_first_post', '2048a082fd924a742f9d92b83c24092af8309a72',
		'This is my first post!', 1);

-- States
INSERT INTO post_state (post_state_id, read, stared, post_id, user_id)
VALUES(1, 1, 1, 1, 1);

-- Updates
INSERT INTO feed_update (feed_update_id, total_count, new_count, updated, feed_id)
VALUES (1, 120, 13, '2014-10-17 12:00:01', 1);
INSERT INTO feed_update (feed_update_id, total_count, new_count, updated, feed_id)
VALUES (2, 120, 5, '2014-10-17 14:38:00', 1);
INSERT INTO feed_update (feed_update_id, total_count, new_count, updated, feed_id)
VALUES (3, 980, 63, '2014-10-17 18:00:01', 2);
INSERT INTO feed_update (feed_update_id, total_count, new_count, updated, feed_id)
VALUES (4, 827, 534, '2014-10-17 13:01:00', 2);
INSERT INTO feed_update (feed_update_id, total_count, new_count, updated, feed_id)
VALUES (5, 827, 0, '2014-10-17 12:00:00', 2);
INSERT INTO feed_update (feed_update_id, total_count, new_count, updated, feed_id)
VALUES (6, 0, 0, '2014-10-17 12:00:00', 3);
