package com.hawkprime.syndicate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Config;
import com.hawkprime.syndicate.util.ConfigType;

/**
 * Configuration DAO.
 */
@Repository
public class ConfigDao extends AbstractDao<Config> {

	/**
	 * Gets the config.
	 *
	 * @param section the section
	 * @param refId the ref id
	 * @return the config
	 */
	public List<Config> getConfiguration(final String section, final Long refId) {
		if (section == null) {
			throw new NullPointerException("Section cannot be null");
		}
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Config> query = cb.createQuery(Config.class);
		final Root<Config> root = query.from(Config.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(cb.equal(root.get("section"), section));

		if (refId != null) {
			predicates.add(cb.equal(root.get("referenceId"), refId));
		} else {
			predicates.add(cb.isNull(root.get("referenceId")));
		}

		query.select(root).where(predicates.toArray(new Predicate[]{}));
		return getEntityManager().createQuery(query).getResultList();
	}

	/**
	 * Gets the config value.
	 *
	 * @param section the section
	 * @param key the key
	 * @param type the type
	 * @param refId the ref id
	 * @return the config value
	 */
	public Object getConfigValue(final String section, final String key, final ConfigType type, final Long refId) {
		if (section == null) {
			throw new NullPointerException("Section cannot be null");
		}
		if (key == null) {
			throw new NullPointerException("Key cannot be null");
		}
		if (type == null) {
			throw new NullPointerException("Value type cannot be null");
		}
		try {
			final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<Config> query = cb.createQuery(Config.class);
			final Root<Config> root = query.from(Config.class);
			final List<Predicate> predicates = new ArrayList<Predicate>();

			predicates.add(cb.equal(root.get("section"), section));
			predicates.add(cb.equal(root.get("key"), key));
			predicates.add(cb.equal(root.get("type"), type));

			if (refId != null) {
				predicates.add(cb.equal(root.get("referenceId"), refId));
			} else {
				predicates.add(cb.isNull(root.get("referenceId")));
			}

			query.select(root).where(predicates.toArray(new Predicate[]{}));
			return getEntityManager().createQuery(query).getSingleResult().getValue();
		} catch (final NoResultException ex) {
			return null;
		}
	}
}
