-- Feeds
INSERT INTO feed (feed_id, name, url)
VALUES (1, 'MyFeed', 'http://myfeed.com/rss');

-- Posts
INSERT INTO post (post_id, title, published, link, guid, text, feed_id)
VALUES (1, 'My First Post', '2014-10-17 14:39:00', 'http://myfeed.com/my_first_post',
		'2048a082fd924a742f9d92b83c24092af8309a72', 'This is my first post!', 1);

-- Updates
INSERT INTO feed_update (feed_update_id, total_count, new_count, updated, feed_id)
VALUES (1, 1, 1, '2014-10-17 12:00:01', 1);
INSERT INTO feed_update (feed_update_id, total_count, new_count, updated, feed_id)
VALUES (2, 1, 0, '2014-10-17 14:38:00', 1);