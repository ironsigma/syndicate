package com.hawkprime.syndicate.service;

import com.hawkprime.syndicate.dao.PostDao;
import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.util.NodePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Post Service.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Service
public class PostService {
	@Autowired
	private ConfigurationService configService;

	@Autowired
	private PostDao postDao;

	/**
	 * Save post.
	 * @param post post
	 * @return 1 if post was saved, 0 if post already exists
	 */
	@Transactional
	public int save(final Post post) {
		if (postDao.doesPosExistsWithGuid(post.getGuid())) {
			return 0;
		}
		postDao.create(post);
		return 1;
	}

	/**
	 * Delete unread not stared posts.
	 * @param feedId feed id
	 * @param days anything prior to this will be deleted
	 * @return number of posts deleted
	 */
	@Transactional
	public int deleteUnreadNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		return postDao.deleteUnreadNotStaredByFeedIdOlderThan(feedId, days);
	}

	/**
	 * Delete read not stared posts.
	 * @param feedId feed id
	 * @param days anything prior to this will be deleted
	 * @return number of posts deleted
	 */
	@Transactional
	public int deleteReadNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		return postDao.deleteReadNotStaredByFeedIdOlderThan(feedId, days);
	}

	/**
	 * Delete published not stared posts.
	 * @param feedId feed id
	 * @param days anything prior to this will be deleted
	 * @return number of posts deleted
	 */
	@Transactional
	public int deletePublishedNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		return postDao.deletePublishedNotStaredByFeedIdOlderThan(feedId, days);
	}

	/**
	 * Clean-up old posts in feed.
	 * @param feed feed to cleanup
	 */
	@Transactional
	public void deleteOldPosts(final Feed feed) {
		final NodePath feedRootConfig = NodePath.at("App/Feed/", feed.getId());
		deleteReadNotStaredByFeedIdOlderThan(feed.getId(),
				configService.getValue(feedRootConfig.append("DeleteReadAfterDays"), Integer.class));

		deleteUnreadNotStaredByFeedIdOlderThan(feed.getId(),
				configService.getValue(feedRootConfig.append("DeleteUnReadAfterDays"), Integer.class));

		deletePublishedNotStaredByFeedIdOlderThan(feed.getId(),
				configService.getValue(feedRootConfig.append("DeletePublishedAfterDays"), Integer.class));
	}

	/**
	 * Set post DAO.
	 * @param postDao dao
	 */
	public void setPostDao(final PostDao postDao) {
		this.postDao = postDao;
	}

}

