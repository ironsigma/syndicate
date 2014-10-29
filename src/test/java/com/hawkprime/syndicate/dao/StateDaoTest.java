package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.State;
import com.hawkprime.syndicate.model.builder.StateBuilder;

/**
 * State DAO Tests.
 */
public class StateDaoTest extends AbstractDaoTest {
	@Autowired
	private StateDao stateDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private UserDao userDao;

	@Test
	public void findAllTest() {
		final List<State> allStates = stateDao.findAll();
		assertThat(allStates.size(), is(1));
	}

	@Test
	public void readStateTest() {
		final State state = stateDao.findById(1L);
		assertThat(state.isRead(), is(true));
		assertThat(state.isStared(), is(true));
		assertThat(state.getPost().getId(), is(1L));
		assertThat(state.getUser().getId(), is(1L));
	}

	@Test
	@Transactional
	public void createStateTest() {
		State state = new StateBuilder()
				.withRead(true)
				.withStared(true)
				.withPost(postDao.findById(1L))
				.withUser(userDao.findById(1L))
				.build();

		stateDao.create(state);

		final Long id = state.getId();
		state = null;

		state = stateDao.findById(id);

		assertThat(state.isRead(), is(true));
		assertThat(state.isStared(), is(true));
		assertThat(state.getPost().getId(), is(1L));
		assertThat(state.getUser().getId(), is(1L));
	}

	@Test
	@Transactional
	public void updateStateTest() {
		State state = new StateBuilder()
				.withRead(false)
				.withStared(true)
				.withPost(postDao.findById(1L))
				.withUser(userDao.findById(1L))
				.build();

		// persist
		stateDao.create(state);

		// clear out
		final Long id = state.getId();
		state = null;

		// fetch back
		state = stateDao.findById(id);

		// update state
		state.setRead(true);
		state.setStared(false);

		// persist
		stateDao.update(state);

		// clear out
		state = null;

		// fetch back
		state = stateDao.findById(id);

		// test
		assertThat(state.isRead(), is(true));
		assertThat(state.isStared(), is(false));
	}

	@Test
	@Transactional
	public void deleteStateTest() {
		State state = new StateBuilder()
				.withPost(postDao.findById(1L))
				.withUser(userDao.findById(2L))
				.build();

		stateDao.create(state);

		final Long id = state.getId();
		state = null;

		stateDao.delete(id);

		state = stateDao.findById(id);
		assertThat(state, is(nullValue()));
	}
}
