package com.trinetra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trinetra.model.AdminClass;
import com.trinetra.model.UserClass;

public interface AdminRepository extends JpaRepository<AdminClass, Integer> {
	
	boolean existsByUsernameAndPassword(String username, String password);

}
