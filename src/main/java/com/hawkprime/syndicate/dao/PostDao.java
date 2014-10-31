package com.hawkprime.syndicate.dao;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Post;

/**
 * Post DAO.
 */
@Repository
public class PostDao extends AbstractDao<Post> {

	/**
	 * Check if a post exist.
	 * @param guid GUI of post to check
	 * @return true if post exists, false otherwise
	 */
	public boolean doesPosExistsWithGuid(final String guid) {
		return 0 != (Long) getEntityManager()
				.createQuery("SELECT COUNT(guid) FROM Post WHERE guid=:guid")
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
				.createQuery("SELECT p.id "
						+ "FROM Post p LEFT JOIN p.states s "
						+ "WHERE p.feed.id = :id "
						+ "AND (s is NULL OR (s.read = false AND s.stared = false)) "
						+ "AND p.fetched <= :date")
				.setParameter("id", feedId)
				.setParameter("date", LocalDateTime.now().minusDays(days))
				.getResultList();

		for (Long postId : postIdList) {
			getEntityManager()
					.createQuery("DELETE FROM Post p WHERE p.id = :id")
					.setParameter("id", postId)
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
				.createQuery("SELECT p.id "
						+ "FROM Post p JOIN p.states s "
						+ "WHERE p.feed.id = :id "
						+ "AND s.read = true "
						+ "AND s.stared = false "
						+ "AND p.fetched <= :date")
				.setParameter("id", feedId)
				.setParameter("date", LocalDateTime.now().minusDays(days))
				.getResultList();

		for (Long postId : postIdList) {
			getEntityManager()
					.createQuery("DELETE FROM Post p WHERE p.id = :id")
					.setParameter("id", postId)
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
				.createQuery("SELECT p.id "
						+ "FROM Post p LEFT JOIN p.states s "
						+ "WHERE p.feed.id = :id "
						+ "AND (s is NULL OR s.stared = false) "
						+ "AND p.published <= :date")
				.setParameter("id", feedId)
				.setParameter("date", LocalDateTime.now().minusDays(days))
				.getResultList();

		for (Long postId : postIdList) {
			getEntityManager()
					.createQuery("DELETE FROM Post p WHERE p.id = :id")
					.setParameter("id", postId)
					.executeUpdate();
		}

		return postIdList.size();
	}
}
