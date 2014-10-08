package com.hawkprime.syndicate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Home Controller.
 */
@Controller
public class AboutController {
	private static final Logger LOG = LoggerFactory.getLogger(AboutController.class);
	
	@RequestMapping("/about")
	public ModelAndView about() {
		LOG.info("handling: /about");
		ModelAndView mav = new ModelAndView("about");
		return mav;
	}

}
