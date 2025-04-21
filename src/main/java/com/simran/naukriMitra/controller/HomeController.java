package com.simran.naukriMitra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String Home () {
		// TODO Auto-generated method stub
		return "index";
	}

}
