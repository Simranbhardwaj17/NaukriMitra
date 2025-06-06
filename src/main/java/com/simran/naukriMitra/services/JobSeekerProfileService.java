package com.simran.naukriMitra.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.simran.naukriMitra.entity.JobSeekerProfile;
import com.simran.naukriMitra.repository.JobSeekerProfileRepository;

@Service
public class JobSeekerProfileService {

	private final JobSeekerProfileRepository jobSeekerProfileRepository;
	
	public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository) {
		this.jobSeekerProfileRepository = jobSeekerProfileRepository;
	}

	public Optional<JobSeekerProfile> getOne(Integer id) {
		return jobSeekerProfileRepository.findById(id);
	}
}
