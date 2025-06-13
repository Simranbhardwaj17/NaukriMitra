package com.simran.naukriMitra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simran.naukriMitra.entity.JobPostActivity;
import com.simran.naukriMitra.entity.JobSeekerProfile;
import com.simran.naukriMitra.entity.JobSeekerSave;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {
	
	List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);
	
	List<JobSeekerSave> findByJob(JobPostActivity job);
}
