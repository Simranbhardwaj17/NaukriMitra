package com.simran.naukriMitra.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.simran.naukriMitra.entity.RecruiterProfile;
import com.simran.naukriMitra.entity.Users;
import com.simran.naukriMitra.repository.UsersRepository;
import com.simran.naukriMitra.services.RecruiterProfileService;
import com.simran.naukriMitra.util.FileUploadUtil;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {
	
	private final UsersRepository usersRepository;
	private final RecruiterProfileService recruiterProfileService;
	
	public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService) {
		this.usersRepository = usersRepository;
		this.recruiterProfileService = recruiterProfileService;
	}
	
	@GetMapping("/")
	public String recruiterProfile(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();   //retrieve Username 
			Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could " + "not found user"));  //give actual user from DB
			Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());
			
			if (!recruiterProfile.isEmpty()) {
				model.addAttribute("profile", recruiterProfile.get());
			}
		}
		
		return "recruiter_profile";
	}
	
	@PostMapping("/addNew")
	//creates a new recruiter pfp(in memory) based on form data
	public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile multipartFile, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();   //retrieve Username 
			Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could " + "not found user"));
	
			//Associate recruiter profile with existing user a/c
			recruiterProfile.setUserId(users);
			recruiterProfile.setUserAccountId(users.getUserId());
		}
		
		model.addAttribute("profile", recruiterProfile);   //Add that pfp to model
		
		//Now for file upload for recruiter pfp img
		String filename = "";
		if (!multipartFile.getOriginalFilename().equals("")) {
			filename = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
			recruiterProfile.setProfilePhoto(filename);  //set img name in recruiterProfile
		}
		
		RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);   //Save recruiterProfile to DB
		
		String uploadDir = "photos/recruiter/" + savedUser.getUserAccountId();  //for upload directory 
		
		try {
			FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return "redirect:/dashboard/";
	}
}



//So this is where we read the profile image from the request, the multi-part file.
//
//And then we save that image on the server and that directory photo slash recruiter based on that given user's name.
//
//And then finally we simply just return a redirect to the dashboard.