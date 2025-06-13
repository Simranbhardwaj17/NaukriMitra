package com.simran.naukriMitra.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.simran.naukriMitra.entity.JobPostActivity;
import com.simran.naukriMitra.entity.JobSeekerProfile;
import com.simran.naukriMitra.entity.JobSeekerSave;
import com.simran.naukriMitra.repository.JobSeekerSaveRepository;

@Service
public class JobSeekerSaveService {

	private final JobSeekerSaveRepository jobSeekerSaveRepository;

	public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
		this.jobSeekerSaveRepository = jobSeekerSaveRepository;
	}
	
	public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
		return jobSeekerSaveRepository.findByUserId(userAccountId);
	}
	
	public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
		return jobSeekerSaveRepository.findByJob(job);
	}
}
