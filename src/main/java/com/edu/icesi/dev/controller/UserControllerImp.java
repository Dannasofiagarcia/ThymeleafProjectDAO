package com.edu.icesi.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserControllerImp implements UserController {

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

}
