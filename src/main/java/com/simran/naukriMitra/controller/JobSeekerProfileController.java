package com.simran.naukriMitra.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simran.naukriMitra.entity.Skills;
import com.simran.naukriMitra.entity.Users;
import com.simran.naukriMitra.repository.UsersRepository;
import com.simran.naukriMitra.services.JobSeekerProfileService;
import com.simran.naukriMitra.entity.JobSeekerProfile;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

	private JobSeekerProfileService jobSeekerProfileService;
	
	private UsersRepository usersRepository;

	@Autowired
	public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UsersRepository usersRepository) {
		this.jobSeekerProfileService = jobSeekerProfileService;
		this.usersRepository = usersRepository;
	}
	
	@GetMapping("/")
	public String JobSeekerProfile(Model model) {
		
		JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Skills> skills = new ArrayList<>();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));  //give actual user from DB
			Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
			if (seekerProfile.isPresent()) {
				jobSeekerProfile = seekerProfile.get();
				if (jobSeekerProfile.getSkills().isEmpty()) {
					skills.add(new Skills());
					jobSeekerProfile.setSkills(skills);
					
				}
			}
			
			model.addAttribute("skills", skills);
			model.addAttribute("profile", jobSeekerProfile);
			
		}	
		
		return "job-seeker-profile";   //connect to html
	}
}
