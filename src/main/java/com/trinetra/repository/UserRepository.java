package com.trinetra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trinetra.model.UserClass;

public interface UserRepository extends JpaRepository<UserClass, Long> {
	
	boolean existsByUsernameAndPassword(String username, String password);
	
	UserClass findByUsername(String username);
}
