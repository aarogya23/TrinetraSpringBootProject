package com.trinetra.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.trinetra.model.ProfileClass;


public interface ProfileRepository extends JpaRepository<ProfileClass, Long> {


}

