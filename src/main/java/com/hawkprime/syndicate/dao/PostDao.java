package com.hawkprime.syndicate.dao;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Post;

/**
 * Post DAO.
 */
@Repository
public class PostDao extends AbstractDao<Post> {
	public boolean doesPosExistsWithGuid(final String guid) {
		return 0 != (Long) getEntityManager()
				.createQuery("SELECT COUNT(guid) FROM Post WHERE guid=:guid")
				.setParameter("guid", guid)
				.getSingleResult();
	}

	public int deleteUnreadNotStaredOlderThan(final int days) {
		@SuppressWarnings("unchecked")
		final List<Long> postIdList = getEntityManager()
				.createQuery("SELECT p.id "
						+ "FROM Post p LEFT JOIN p.states s "
						+ "WHERE (s is NULL OR (s.read = false AND s.stared = false)) "
						+ "AND p.fetched <= :date")
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

	public int deleteReadNotStaredOlderThan(final int days) {
		@SuppressWarnings("unchecked")
		final List<Long> postIdList = getEntityManager()
				.createQuery("SELECT p.id "
						+ "FROM Post p JOIN p.states s "
						+ "WHERE s.read = true "
						+ "AND s.stared = false "
						+ "AND p.fetched <= :date")
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

	public int deletePublishedNotStaredOlderThan(final int days) {
		@SuppressWarnings("unchecked")
		final List<Long> postIdList = getEntityManager()
				.createQuery("SELECT p.id "
						+ "FROM Post p LEFT JOIN p.states s "
						+ "WHERE (s is NULL OR s.stared = false) "
						+ "AND p.published <= :date")
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
