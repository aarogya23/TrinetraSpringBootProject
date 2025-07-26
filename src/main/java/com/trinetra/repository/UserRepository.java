package com.trinetra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trinetra.model.UserClass;

public interface UserRepository extends JpaRepository<UserClass, Long> {
	
	   UserClass findByUsername(String username);
	    
	    // Optional: Find by email (if you want to allow email login)
	    UserClass findByEmail(String email);
	    
	    // Optional: Check if username exists (for validation)
	    boolean existsByUsername(String username);
	    
	    // Optional: Check if email exists (for validation)
	    boolean existsByEmail(String email);
}
