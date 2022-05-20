package com.edu.icesi.dev;

import java.sql.Timestamp;
import java.util.Date;

import com.edu.icesi.dev.model.UserApp;
import com.edu.icesi.dev.model.UserType;
import com.edu.icesi.dev.service.ScrapreasonServiceImp;
import com.edu.icesi.dev.service.UnitmeasureServiceImp;
import com.edu.icesi.dev.service.UserServiceImp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
@ComponentScan("com.edu.icesi.dev")
public class Taller1DannaGarciaApplication {

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	// CommandLine runner
	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(Taller1DannaGarciaApplication.class, args);

		UserServiceImp us = c.getBean(UserServiceImp.class);
		UnitmeasureServiceImp um = c.getBean(UnitmeasureServiceImp.class);
		ScrapreasonServiceImp sr = c.getBean(ScrapreasonServiceImp.class);

		sr.saveScrapreason("Falla en empaque", new Timestamp(System.currentTimeMillis()));
		sr.saveScrapreason("Falla en contenido", new Timestamp(System.currentTimeMillis()));

		um.saveUnitmeasure("Kg", new Date());
		um.saveUnitmeasure("Pounds", new Date());

		UserApp user1 = new UserApp();
		user1.setUsername("admin");
		user1.setPassword("{noop}password");
		user1.setName("Administrador");
		user1.setEmail("admin@gmail.com");
		user1.setType(UserType.administrator);
		us.save(user1);

		UserApp user2 = new UserApp();
		user2.setUsername("operator");
		user2.setPassword("{noop}password");
		user2.setName("Operator");
		user2.setEmail("operator@gmail.com");
		user2.setType(UserType.operator);
		us.save(user2);

	}

}
