package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.model.State;
import com.hawkprime.syndicate.model.User;
import com.hawkprime.syndicate.model.builder.PostBuilder;
import com.hawkprime.syndicate.model.builder.StateBuilder;
import com.hawkprime.syndicate.model.builder.UserBuilder;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

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

	@Autowired
	private FeedDao feedDao;

	/**
	 * Find all entities in table.
	 */
	@Test
	public void findAllTest() {
		List<State> allStates = stateDao.findAll();
		assertThat(allStates.size(), is(1));
	}

	/**
	 * Read entities.
	 */
	@Test
	public void readStateTest() {
		State state = stateDao.findById(1L);
		assertThat(state.isRead(), is(true));
		assertThat(state.isStared(), is(true));
		assertThat(state.getPost().getId(), is(1L));
		assertThat(state.getUser().getId(), is(1L));
	}

	/**
	 * Create entities.
	 */
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

	/**
	 * Update entities.
	 */
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

	/**
	 * Delete entities.
	 */
	@Test
	@Transactional
	public void deleteStateTest() {
		final long feedId = 1L;
		final long userId = 2L;

		State state = new StateBuilder()
				.withPost(postDao.findById(feedId))
				.withUser(userDao.findById(userId))
				.build();

		stateDao.create(state);

		final Long id = state.getId();
		state = null;

		stateDao.deleteById(id);

		state = stateDao.findById(id);
		assertThat(state, is(nullValue()));
	}

	/**
	 * Delete post with state cascade.
	 */
	@Test
	@Transactional
	public void deletePostCascadeTest() {
		Post post = new PostBuilder()
			.withFeed(feedDao.findById(1L))
			.build();

		postDao.create(post);
		final long postId = post.getId();

		final long userId = 2L;
		State state = new StateBuilder()
			.withPost(postDao.findById(postId))
			.withUser(userDao.findById(userId))
			.build();

		stateDao.create(state);
		postDao.getEntityManager().clear();
		postDao.getEntityManager().flush();

		final long stateId = state.getId();
		post = null;
		state = null;

		postDao.deleteById(postId);
		post = postDao.findById(postId);
		assertThat(post, is(nullValue()));

		state = stateDao.findById(stateId);
		assertThat(state, is(nullValue()));
	}

	/**
	 * Delete User with state cascade.
	 */
	@Test
	@Transactional
	public void deleteUserTest() {
		User user = new UserBuilder().build();

		userDao.create(user);
		final long userId = user.getId();

		State state = new StateBuilder()
			.withPost(postDao.findById(1L))
			.withUser(userDao.findById(userId))
			.build();

		stateDao.create(state);
		postDao.getEntityManager().clear();
		postDao.getEntityManager().flush();

		final long stateId = state.getId();
		user = null;
		state = null;

		userDao.deleteById(userId);
		user = userDao.findById(userId);
		assertThat(user, is(nullValue()));

		state = stateDao.findById(stateId);
		assertThat(state, is(nullValue()));
	}
}
