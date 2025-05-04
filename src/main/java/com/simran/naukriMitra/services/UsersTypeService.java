package com.simran.naukriMitra.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simran.naukriMitra.entity.UsersType;
import com.simran.naukriMitra.repository.UsersTypeRepository;

@Service
public class UsersTypeService {
	
	private final UsersTypeRepository usersTypeRepository;

	@Autowired
	public UsersTypeService(UsersTypeRepository usersTypeRepository) {
		this.usersTypeRepository = usersTypeRepository;
	}
	
	public List<UsersType> getAll() {
		return usersTypeRepository.findAll() ;
	}

}


//feature: Add UsersTypeService to handle business logic for user types
//
//- Created UsersTypeService class to encapsulate business logic related to UsersType entities
//- Injected UsersTypeRepository via constructor for better testability and immutability
//- Added getAll() method to fetch all user types from the database
//- Annotated with @Service to allow Spring to manage this component

