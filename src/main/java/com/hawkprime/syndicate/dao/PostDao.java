package com.hawkprime.syndicate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Post;

/**
 * Post DAO.
 */
@Repository
public class PostDao extends GenericDao<Post> {
	@Transactional(readOnly=true)
	public boolean isPosExistsByGuid(String guid) {
		return 0 != (Long) entityManager
				.createQuery("SELECT COUNT(guid) FROM Post WHERE guid=:guid")
				.setParameter("guid", guid)
				.getSingleResult();
	}
}