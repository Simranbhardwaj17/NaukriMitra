package com.simran.naukriMitra.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simran.naukriMitra.entity.RecruiterProfile;
import com.simran.naukriMitra.repository.RecruiterProfileRepository;

@Service
public class RecruiterProfileService {

	private final RecruiterProfileRepository recruiterRepository;

	@Autowired
	public RecruiterProfileService(RecruiterProfileRepository recruiterRepository) {
		this.recruiterRepository = recruiterRepository;
	}

	public Optional<RecruiterProfile> getOne(Integer id) {
		return recruiterRepository.findById(id);
	}

	public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
		return recruiterRepository.save(recruiterProfile);
	}
	
}
