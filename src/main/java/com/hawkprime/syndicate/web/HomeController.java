package com.hawkprime.syndicate.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hawkprime.syndicate.dao.FeedDao;
import com.hawkprime.syndicate.model.Feed;

/**
 * Home Controller.
 */
@Controller
public class HomeController {
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private FeedDao feedDao;

	@RequestMapping({"/", "/home"})
	public ModelAndView home() {
		LOG.info("handling: /home");
		List<Feed> feeds = feedDao.list();
		ModelAndView mav = new ModelAndView("home");
		mav.getModel().put("name", feeds.get(0).getName());
		mav.getModel().put("url", feeds.get(0).getUrl());
		return mav;
	}

}
