//UsersTypeRepository is basically our roles for the users
package com.simran.naukriMitra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simran.naukriMitra.entity.UsersType;

public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {

}
