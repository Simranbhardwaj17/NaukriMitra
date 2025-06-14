package com.simran.naukriMitra.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.simran.naukriMitra.entity.JobPostActivity;
import com.simran.naukriMitra.entity.JobSeekerApply;
import com.simran.naukriMitra.entity.JobSeekerProfile;
import com.simran.naukriMitra.entity.JobSeekerSave;
import com.simran.naukriMitra.entity.RecruiterProfile;
import com.simran.naukriMitra.entity.Users;
import com.simran.naukriMitra.services.JobPostActivityService;
import com.simran.naukriMitra.services.JobSeekerApplyService;
import com.simran.naukriMitra.services.JobSeekerProfileService;
import com.simran.naukriMitra.services.JobSeekerSaveService;
import com.simran.naukriMitra.services.RecruiterProfileService;
import com.simran.naukriMitra.services.UsersService;

@Controller
public class JobSeekerApplyController {

	private final JobPostActivityService jobPostActivityService;
	private final UsersService usersService;
	private final JobSeekerApplyService jobSeekerApplyService;
	private final JobSeekerSaveService jobSeekerSaveService;
	private final RecruiterProfileService recruiterProfileService;
	private final JobSeekerProfileService jobSeekerProfileService;
	
	@Autowired
	public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UsersService usersService, JobSeekerApplyService jobSeekerApplyService, JobSeekerSaveService jobSeekerSaveService, JobSeekerProfileService jobSeekerProfileService, RecruiterProfileService recruiterProfileService) {
		this.jobPostActivityService = jobPostActivityService;
		this.usersService = usersService;
		this.jobSeekerApplyService = jobSeekerApplyService;
		this.jobSeekerSaveService = jobSeekerSaveService;
		this.recruiterProfileService = recruiterProfileService;
		this.jobSeekerProfileService = jobSeekerProfileService;
	}
	
	//Mthd to display details for given job
	@GetMapping("job-details-apply/{id}")
	public String display(@PathVariable("id") int id, Model model) {
		JobPostActivity jobDetails = jobPostActivityService.getOne(id);
		List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
		List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
                if (user != null) {
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            } else {
                JobSeekerProfile user = jobSeekerProfileService.getCurrentSeekerProfile();
                if (user != null) {
                    boolean exists = false;
                    boolean saved = false;
                    for (JobSeekerApply jobSeekerApply : jobSeekerApplyList) {
                        if (jobSeekerApply.getUserId().getUserAccountId() == user.getUserAccountId()) {
                            exists = true;
                            break;
                        }
                    }
                    for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
                        if (jobSeekerSave.getUserId().getUserAccountId() == user.getUserAccountId()) {
                            saved = true;
                            break;
                        }
                    }
                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }

        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        model.addAttribute("applyJob", jobSeekerApply);
		
		model.addAttribute("jobDetails", jobDetails);
		model.addAttribute("user", usersService.getCurrentUserProfile());
		return "job-details";
	}
	
	@PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id") int id, JobSeekerApply jobSeekerApply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
            if (seekerProfile.isPresent() && jobPostActivity != null) {
                jobSeekerApply = new JobSeekerApply();
                jobSeekerApply.setUserId(seekerProfile.get());
                jobSeekerApply.setJob(jobPostActivity);
                jobSeekerApply.setApplyDate(new Date());
            } else {
                throw new RuntimeException("User not found");
            }
            jobSeekerApplyService.addNew(jobSeekerApply);
        }

        return "redirect:/dashboard/";
    }
}
