-- Feeds
INSERT INTO feed (feed_id, name, url)
VALUES (1, 'MyFeed', 'http://myfeed.com/rss');
INSERT INTO feed (feed_id, name, url)
VALUES (2, 'Another Feed', 'http://anotherfeed.com/rss');

-- Posts
INSERT INTO post (post_id, title, published, link, guid, text, feed_id)
VALUES (1, 'My First Post', '2014-10-17 14:39:00', 'http://myfeed.com/my_first_post',
		'2048a082fd924a742f9d92b83c24092af8309a72', 'This is my first post!', 1);

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
