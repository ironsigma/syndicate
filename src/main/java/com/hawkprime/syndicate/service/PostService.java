package com.hawkprime.syndicate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.dao.PostDao;
import com.hawkprime.syndicate.model.Post;

/**
 * Post Service.
 */
@Service
public class PostService {

	@Autowired
	private PostDao postDao;

	@Transactional
	public int save(final Post post) {
		if (postDao.doesPosExistsWithGuid(post.getGuid())) {
			return 0;
		}
		postDao.create(post);
		return 1;
	}

	public void setPostDao(final PostDao postDao) {
		this.postDao = postDao;
	}

	public int deleteUnreadNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		return postDao.deleteUnreadNotStaredByFeedIdOlderThan(feedId, days);
	}

	public int deleteReadNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		return postDao.deleteReadNotStaredByFeedIdOlderThan(feedId, days);
	}

	public int deletePublishedNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		return postDao.deletePublishedNotStaredByFeedIdOlderThan(feedId, days);
	}
}
