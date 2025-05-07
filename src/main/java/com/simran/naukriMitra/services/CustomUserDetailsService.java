package com.simran.naukriMitra.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.simran.naukriMitra.entity.Users;
import com.simran.naukriMitra.repository.UsersRepository;
import com.simran.naukriMitra.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UsersRepository usersRepository;

	@Autowired
	public CustomUserDetailsService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  //Tell Spring security how to retrieve a user from the DB
		Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found user"));
		return new CustomUserDetails(user);
	}

}
