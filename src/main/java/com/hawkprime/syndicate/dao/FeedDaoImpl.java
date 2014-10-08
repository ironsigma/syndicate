package com.hawkprime.syndicate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Feed;

/**
 * Feed DAO.
 */
@Repository
public class FeedDaoImpl implements FeedDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional(readOnly=true)
	public List<Feed> list() {
		return (List<Feed>) entityManager
				.createQuery("select f from Feed f")
				.getResultList();
	}
}
