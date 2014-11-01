-- Config
INSERT INTO config (config_id, value_type, string_value, entity_type, entity_id)
VALUES ('user.01.name', 'STRING', 'Joe Hawk', 'User', 1);

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
