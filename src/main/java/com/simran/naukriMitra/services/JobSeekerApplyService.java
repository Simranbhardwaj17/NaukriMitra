package com.simran.naukriMitra.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simran.naukriMitra.entity.JobPostActivity;
import com.simran.naukriMitra.entity.JobSeekerApply;
import com.simran.naukriMitra.entity.JobSeekerProfile;
import com.simran.naukriMitra.repository.JobSeekerApplyRepository;

@Service
public class JobSeekerApplyService {
	
	private final JobSeekerApplyRepository jobSeekerApplyRepository;
	
	@Autowired
	public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
		this.jobSeekerApplyRepository = jobSeekerApplyRepository;
	}
	
	public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
		return jobSeekerApplyRepository.findByUserId(userAccountId);
	}
	
	public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
		return jobSeekerApplyRepository.findByJob(job);
	}
	
	public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepository.save(jobSeekerApply);
    }
} 
