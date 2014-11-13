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
		NodePath app = NodePath.at("/App");

		assertThat(nodeDao.findClosestByPath(NodePath.root()).getPath(), is(NodePath.root().toString()));
		assertThat(nodeDao.findClosestByPath(app).getPath(), is(app.toString()));
		assertThat(nodeDao.findClosestByPath(NodePath.at("/App/xxxNonExistingxxx")).getPath(), is(app.toString()));
		assertThat(nodeDao.findClosestByPath(null), is(nullValue()));
	}

}
