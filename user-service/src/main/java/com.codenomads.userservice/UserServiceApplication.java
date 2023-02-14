package com.codenomads.userservice;

import com.codenomads.userservice.model.User;
import com.codenomads.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository userRepository) {
		return args -> {
			User user = User.builder()
					.name("Sebastian")
					.email("sebastian@gmail.com")
					.birthDate(LocalDate.of(2010, Month.NOVEMBER, 2))
					.build();

			User user1 = User.builder()
					.name("Viktor")
					.email("viktor@gmail.com")
					.birthDate(LocalDate.of(2000, Month.JANUARY, 30))
					.build();

			userRepository.save(user);
			userRepository.save((user1));
		};
	}
}
