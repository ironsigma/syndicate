package com.hawkprime.syndicate.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Feed;

/**
 * Feed DAO.
 */
@Repository
public class FeedDaoImpl implements FeedDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly=true)
	public List<Feed> list() {
		return (List<Feed>) sessionFactory.getCurrentSession()
				.createCriteria(Feed.class)
				.list();
	}
}
