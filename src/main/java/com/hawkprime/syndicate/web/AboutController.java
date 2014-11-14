package com.hawkprime.syndicate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Home Controller.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Controller
public class AboutController {
	private static final Logger LOG = LoggerFactory.getLogger(AboutController.class);

	/**
	 * About page.
	 * @return model and view
	 */
	@RequestMapping("/about")
	public ModelAndView about() {
		LOG.info("handling: /about");
		final ModelAndView mav = new ModelAndView("about");
		return mav;
	}

}
