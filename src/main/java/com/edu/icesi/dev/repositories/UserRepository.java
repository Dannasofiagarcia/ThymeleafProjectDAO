package com.edu.icesi.dev.repositories;

import java.util.List;

import com.edu.icesi.dev.model.UserApp;
import com.edu.icesi.dev.model.UserType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserApp, Integer> {

	List<UserApp> findByName(String name);

	List<UserApp> findByType(UserType user);

	List<UserApp> findByUsername(String username);

}