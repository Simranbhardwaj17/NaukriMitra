package com.simran.naukriMitra.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.simran.naukriMitra.entity.Skills;
import com.simran.naukriMitra.entity.Users;
import com.simran.naukriMitra.repository.UsersRepository;
import com.simran.naukriMitra.services.JobSeekerProfileService;
import com.simran.naukriMitra.util.FileUploadUtil;
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
	
	@PostMapping("/addNew")
	public String addNew(JobSeekerProfile jobSeekerProfile,
			@RequestParam("image") MultipartFile image,
			@RequestParam("pdf") MultipartFile pdf,
			Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
			jobSeekerProfile.setUserId(user);
			jobSeekerProfile.setUserAccountId(user.getUserId());
		}
		
		List<Skills> skillsList = new ArrayList<>();
		model.addAttribute("profile", jobSeekerProfile);
		model.addAttribute("skills", skillsList);
		
		for (Skills skills : jobSeekerProfile.getSkills()) {
			skills.setJobSeekerProfile(jobSeekerProfile);
		}
		
		String imageName = "";
		String resumeName = "";
		
		if (!Objects.equals(image.getOriginalFilename(), "")) {
			imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
			jobSeekerProfile.setProfilePhoto(imageName);
		}
		
		if (!Objects.equals(pdf.getOriginalFilename(), "")) {
			resumeName = StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename()));
			jobSeekerProfile.setResume(resumeName);
		}
		
		//Its just in memory, to save these in DB use service
		JobSeekerProfile seekerProfile = jobSeekerProfileService.addNew(jobSeekerProfile);
		
		//these save the file into filesystem
		try {
			String uploadDir = "photos/candidate/" + jobSeekerProfile.getUserAccountId();
			
			if (!Objects.equals(image.getOriginalFilename(), "")) {
				FileUploadUtil.saveFile(uploadDir, imageName, image);
			}
			
			if (!Objects.equals(pdf.getOriginalFilename(), "")) {
				FileUploadUtil.saveFile(uploadDir, resumeName, pdf);
			}
			
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
		return "redirect:/dashboard/";
	}
	
	 @GetMapping("/{id}")
	    public String candidateProfile(@PathVariable("id") int id, Model model) {

	        Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(id);
	        model.addAttribute("profile", seekerProfile.get());
	        return "job-seeker-profile";
	    }
	 
	 @GetMapping("/downloadResume")
	    public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName, @RequestParam(value = "userID") String userId) {

	        FileDownloadUtil downloadUtil = new FileDownloadUtil();
	        Resource resource = null;

	        try {
	            resource = downloadUtil.getFileAsResourse("photos/candidate/" + userId, fileName);
	        } 

	    }
	 
}
