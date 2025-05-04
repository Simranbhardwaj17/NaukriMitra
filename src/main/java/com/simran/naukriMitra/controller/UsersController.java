package com.simran.naukriMitra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.simran.naukriMitra.entity.Users;
import com.simran.naukriMitra.entity.UsersType;
import com.simran.naukriMitra.services.UsersService;

import com.simran.naukriMitra.services.UsersTypeService;

import jakarta.validation.Valid;

@Controller
public class UsersController {
	
	private final UsersTypeService usersTypeService;
	private final UsersService usersService;

	@Autowired
	public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
		this.usersTypeService = usersTypeService;
		this.usersService = usersService;
	}
	
	@GetMapping("/register")
	//Method to show user registration form
	public String register(Model model) {   //use model to kind of pre-populate some basic form data 
		List<UsersType> usersTypes = usersTypeService.getAll();
		model.addAttribute("getAllTypes", usersTypes);
		model.addAttribute("user", new Users());
		return "register";
	}
	
	@PostMapping("/register/new")
	public String userRegistration(@Valid Users users) {
//		System.out.println("User:: " +users);
		usersService.addNew(users);
		return "dashboard";
	}
	
}
