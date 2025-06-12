package com.simran.naukriMitra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simran.naukriMitra.entity.JobPostActivity;
import com.simran.naukriMitra.entity.JobSeekerApply;
import com.simran.naukriMitra.entity.JobSeekerProfile;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {

	List<JobSeekerApply> findByUserId(JobSeekerProfile userId);
	
	List<JobSeekerApply> findByJob(JobPostActivity job);
	
} 
