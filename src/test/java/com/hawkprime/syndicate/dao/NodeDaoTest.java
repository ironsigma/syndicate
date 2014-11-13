package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.util.NodePath;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Node DAO Tests.
 */
public class NodeDaoTest extends AbstractDaoTest {
	@Autowired
	private NodeDao nodeDao;

	/**
	 * Find closest by path test.
	 */
	@Test
	public void findClosestByPathTest() {
		assertThat(nodeDao.findClosestByPath(NodePath.root()).getPath(), is("/"));
		assertThat(nodeDao.findClosestByPath(NodePath.at("/App")).getPath(), is("/App"));
		assertThat(nodeDao.findClosestByPath(NodePath.at("/App/xxxNonExistingxxx")).getPath(), is("/App"));
		assertThat(nodeDao.findClosestByPath(null), is(nullValue()));
	}

}
