package com.simran.naukriMitra.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.simran.naukriMitra.entity.JobPostActivity;
import com.simran.naukriMitra.entity.RecruiterJobsDto;
import com.simran.naukriMitra.repository.JobPostActivityRepository;

@Service
public class JobPostActivityService {

	private final JobPostActivityRepository jobPostActivityRepository;
	
	public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
		this.jobPostActivityRepository = jobPostActivityRepository;
	}

	public JobPostActivity addNew(JobPostActivity jobPostActivity) {
		return jobPostActivityRepository.save(jobPostActivity);
	}  
	
	public List<RecruiterJobsDto> getRecruiterJobs(int recruiter) {
		
	}
}
