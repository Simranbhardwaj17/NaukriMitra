package com.simran.naukriMitra.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.simran.naukriMitra.entity.IRecruiterJobs;
import com.simran.naukriMitra.entity.JobCompany;
import com.simran.naukriMitra.entity.JobLocation;
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
		
		List<IRecruiterJobs> recruiterJobsDtos = jobPostActivityRepository.getRecruiterJobs(recruiter);
		
		List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
		
		//convert information from database to DTOs
		for (IRecruiterJobs rec : recruiterJobsDtos) {
			JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
			JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
			recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(), rec.getJob_title(), loc, comp));
		}
		
		return recruiterJobsDtoList;
	}
}
