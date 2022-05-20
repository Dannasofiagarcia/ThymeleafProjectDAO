package com.edu.icesi.dev.service.interfaces;

import java.util.Optional;

import com.edu.icesi.dev.model.UserApp;
import com.edu.icesi.dev.model.UserType;

public interface UserService {

	public void save(UserApp user);

	public Optional<UserApp> findById(Integer id);

	public Iterable<UserApp> findAll();

	public Iterable<UserApp> findAllPatients();

	public Iterable<UserApp> findAllDoctors();

	public void delete(UserApp user);

	public UserType[] getTypes();

	public boolean confirmPassword(Integer id, String password);

}
