package com.simran.naukriMitra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simran.naukriMitra.entity.Users;    //import Users

public interface UsersRepository extends JpaRepository<Users, Integer> {   //making use of spring data JPA give you some of the common CRUD functionality(as this is an interface as opposed to a class) 

}
