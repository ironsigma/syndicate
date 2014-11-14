package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Node;
import com.hawkprime.syndicate.util.NodePath;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

/**
 * Node DAO.
 */
@Repository
public class NodeDao extends AbstractDao<Node> {

	/**
	 * Find closest node by path.
	 * @param nodePath path
	 * @return Node or null if none found
	 */
	public Node findClosestByPath(final NodePath nodePath) {
		try {
			return (Node) getEntityManager()
					.createNamedQuery("Node.findClosestByPath")
					.setParameter("path", nodePath.toString())
					.setMaxResults(1)
					.getSingleResult();
		} catch (NullPointerException | NoResultException ex) {
			return null;
		}
	}
}
