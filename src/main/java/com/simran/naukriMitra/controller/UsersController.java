package com.simran.naukriMitra.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.simran.naukriMitra.entity.Users;
import com.simran.naukriMitra.entity.UsersType;
import com.simran.naukriMitra.services.UsersService;

import com.simran.naukriMitra.services.UsersTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	public String userRegistration(@Valid Users users, Model model) {
		
		Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
		
		if(optionalUsers.isPresent())
		{
			model.addAttribute("error", "Email already registered, try to login or register with other email.");  //if the email exists, add error message to model and return registration form
			List<UsersType> usersTypes = usersTypeService.getAll();
			model.addAttribute("getAllTypes", usersTypes);
			model.addAttribute("user", new Users());
			return "register";
		}
		
//		System.out.println("User:: " +users);
		usersService.addNew(users);
		return "dashboard";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request,  response, authentication);
		}
		return "redirect:/";
	}
	
}
