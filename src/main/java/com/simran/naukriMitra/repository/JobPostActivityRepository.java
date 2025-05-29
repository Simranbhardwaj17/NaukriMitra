package com.simran.naukriMitra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simran.naukriMitra.entity.JobPostActivity;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Integer> {
	
	List<IRecruiterJobs> getRecruiterJobs(@Param("recruiter") int recruiter);
}
