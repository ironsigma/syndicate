package com.hawkprime.syndicate.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Node;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Node DAO.
 */
@Repository
public class NodeDao extends AbstractDao<Node> {

	public Node findClosestByPath(final NodePath nodePath) {
		if (nodePath == null) {
			return null;
		}
		try {
			return (Node) getEntityManager()
					.createQuery("SELECT n "
							+ "FROM Node n "
							+ "WHERE :path LIKE CONCAT(n.path, '%') "
							+ "ORDER BY LENGTH(n.path) DESC ")
					.setParameter("path", nodePath.toString())
					.setMaxResults(1)
					.getSingleResult();
		} catch (final NoResultException ex) {
			return null;
		}
	}
}
