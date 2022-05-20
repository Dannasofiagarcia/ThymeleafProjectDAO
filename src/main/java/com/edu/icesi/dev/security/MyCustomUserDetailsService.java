package com.edu.icesi.dev.security;

import java.util.List;

import com.edu.icesi.dev.model.UserApp;
import com.edu.icesi.dev.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserApp userApp = null;

		List<UserApp> user = userRepository.findByUsername(username);

		if (user.size() != 0) {
			userApp = user.get(0);

			User.UserBuilder builder = User.withUsername(username).password(userApp.getPassword())
					.roles(String.valueOf(userApp.getType()));
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}