package com.edu.icesi.dev.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.edu.icesi.dev.validated.ValidatedUser;

import lombok.Data;

@Entity
@Data
public class UserApp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(groups = { ValidatedUser.class }, message = "Username cannot be blank")
	private String username;

	@NotBlank(groups = { ValidatedUser.class }, message = "Password cannot be blank")
	private String password;

	@NotBlank(groups = { ValidatedUser.class }, message = "Name cannot be blank")
	private String name;

	@NotBlank(groups = { ValidatedUser.class }, message = "Email cannot be blank")
	@Email(groups = { ValidatedUser.class }, message = "Email enter is no valid")
	private String email;

	@NotNull(groups = { ValidatedUser.class }, message = "User type cannot be blank")
	private UserType type;

}
