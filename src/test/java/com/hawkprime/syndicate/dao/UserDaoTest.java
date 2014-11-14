package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.User;
import com.hawkprime.syndicate.model.builder.UserBuilder;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * User DAO Tests.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class UserDaoTest extends AbstractDaoTest {

	@Autowired
	private UserDao userDao;

	/**
	 * Find all entities in table.
	 */
	@Test
	public void findAllTest() {
		final int totalUsers = 2;
		List<User> allUsers = userDao.findAll();
		assertThat(allUsers.size(), is(totalUsers));
	}

	/**
	 * Read entities.
	 */
	@Test
	public void readUserTest() {
		User user = userDao.findById(1L);
		assertThat(user.getName(), is("Joe Hawk"));
	}

	/**
	 * Create entities.
	 */
	@Test
	@Transactional
	public void createUserTest() {
		final String name = "Another user";

		User user = new UserBuilder()
				.withName(name)
				.build();

		userDao.create(user);

		final Long id = user.getId();
		user = null;

		user = userDao.findById(id);

		assertThat(user.getName(), is(name));
	}

	/**
	 * Update entities.
	 */
	@Test
	@Transactional
	public void updateUserTest() {
		User user = new UserBuilder().build();

		// persist
		userDao.create(user);

		// clear out
		final Long id = user.getId();
		user = null;

		// fetch back
		user = userDao.findById(id);

		// change values
		final String name = "Awsome user";

		// update user
		user.setName(name);

		// persist
		userDao.update(user);

		// clear out
		user = null;

		// fetch back
		user = userDao.findById(id);

		// test
		assertThat(user.getName(), is(name));
	}

	/**
	 * Delete entities.
	 */
	@Test
	@Transactional
	public void deleteUserTest() {
		User user = new UserBuilder().build();

		userDao.create(user);

		final Long id = user.getId();
		user = null;

		userDao.deleteById(id);

		user = userDao.findById(id);
		assertThat(user, is(nullValue()));
	}
}
