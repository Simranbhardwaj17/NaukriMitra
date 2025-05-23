package com.simran.naukriMitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.simran.naukriMitra.entity.JobPostActivity;
import com.simran.naukriMitra.entity.Users;
import com.simran.naukriMitra.services.UsersService;

@Controller
public class JobPostActivityController {
	
	private final UsersService usersService;

	@Autowired
	public JobPostActivityController(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@GetMapping("/dashboard/")
	public String searchJobs(Model model) {
		
		Object currentUserProfile = usersService.getCurrentUserProfile();  //Retrieve from usersService method
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			model.addAttribute("username", currentUsername);
		}
		
		model.addAttribute("user", currentUserProfile);
		System.out.println("Dashboard");		
		return "dashboard";
	}
	
	@GetMapping("/dashboard/add")
	public String addJobs(Model model) {  //new mthd for showing form
		model.addAttribute("jobPostActivity", new JobPostActivity());
		model.addAttribute("user", usersService.getCurrentUserProfile());
		return "add-jobs";
	}
	
	@GetMapping("/dashboard/addNew")
	public String addNew(JobPostActivity jobPostActivity, Model model) {
		
		Users user = usersService.getCurrentUser();
		return "redirect:/dashboard/";
	}
}
