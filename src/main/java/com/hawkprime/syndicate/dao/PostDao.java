package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Post;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

/**
 * Post DAO.
 */
@Repository
public class PostDao extends AbstractDao<Post> {
	private static final String ID = "id";
	private static final String DATE = "date";
	private static final String DELETE_WITH_ID_QUERY = "Post.deleteWithId";

	/**
	 * Check if a post exist.
	 * @param guid GUI of post to check
	 * @return true if post exists, false otherwise
	 */
	public boolean doesPosExistsWithGuid(final String guid) {
		return 0 != (Long) getEntityManager()
				.createNamedQuery("Post.postExistsByGuid")
				.setParameter("guid", guid)
				.getSingleResult();
	}

	/**
	 * Delete posts that are unread and not stared.
	 * @param feedId Feed id to delete from
	 * @param days Any post at or before days will be deleted
	 * @return number of post deleted
	 */
	public int deleteUnreadNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		@SuppressWarnings("unchecked")
		final List<Long> postIdList = getEntityManager()
				.createNamedQuery("Post.unreadNotStaredByFeedIdOlderThan")
				.setParameter(ID, feedId)
				.setParameter(DATE, LocalDateTime.now().minusDays(days))
				.getResultList();

		for (Long postId : postIdList) {
			getEntityManager()
					.createNamedQuery(DELETE_WITH_ID_QUERY)
					.setParameter(ID, postId)
					.executeUpdate();
		}

		return postIdList.size();
	}

	/**
	 * Delete posts that are read and not stared.
	 * @param feedId Feed id to delete from
	 * @param days Any post at or before days will be deleted
	 * @return number of post deleted
	 */
	public int deleteReadNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		@SuppressWarnings("unchecked")
		final List<Long> postIdList = getEntityManager()
				.createNamedQuery("Post.readNotStaredByFeedIdOlderThan")
				.setParameter(ID, feedId)
				.setParameter(DATE, LocalDateTime.now().minusDays(days))
				.getResultList();

		for (Long postId : postIdList) {
			getEntityManager()
					.createNamedQuery(DELETE_WITH_ID_QUERY)
					.setParameter(ID, postId)
					.executeUpdate();
		}

		return postIdList.size();
	}

	/**
	 * Delete posts that are published and not stared.
	 * @param feedId Feed id to delete from
	 * @param days Any post at or before days will be deleted
	 * @return number of post deleted
	 */
	public int deletePublishedNotStaredByFeedIdOlderThan(final long feedId, final int days) {
		@SuppressWarnings("unchecked")
		final List<Long> postIdList = getEntityManager()
				.createNamedQuery("Post.publishedNotStaredByFeedIdOlderThan")
				.setParameter(ID, feedId)
				.setParameter(DATE, LocalDateTime.now().minusDays(days))
				.getResultList();

		for (Long postId : postIdList) {
			getEntityManager()
					.createNamedQuery(DELETE_WITH_ID_QUERY)
					.setParameter(ID, postId)
					.executeUpdate();
		}

		return postIdList.size();
	}
}
