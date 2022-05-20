package com.edu.icesi.dev.service;

import java.util.Optional;

import com.edu.icesi.dev.model.UserApp;
import com.edu.icesi.dev.model.UserType;
import com.edu.icesi.dev.repositories.UserRepository;
import com.edu.icesi.dev.service.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImp(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void save(UserApp user) {
		userRepository.save(user);

	}

	@Override
	public Optional<UserApp> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public Iterable<UserApp> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Iterable<UserApp> findAllPatients() {
		return userRepository.findByType(UserType.administrator);
	}

	@Override
	public Iterable<UserApp> findAllDoctors() {
		return userRepository.findByType(UserType.operator);
	}

	@Override
	public void delete(UserApp user) {
		userRepository.delete(user);
	}

	@Override
	public UserType[] getTypes() {
		// TODO Auto-generated method stub
		return UserType.values();
	}

	@Override
	public boolean confirmPassword(Integer id, String currentPassword) {
		return userRepository.findById(id).get().getPassword().equals(currentPassword);
	}

}
