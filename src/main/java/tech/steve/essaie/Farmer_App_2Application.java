package tech.steve.essaie;

import lombok.AllArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


import tech.steve.essaie.repository.UtilisateurRepository;



@SpringBootApplication
@AllArgsConstructor
public class Farmer_App_2Application {

	UtilisateurRepository utilisateurRepository;
	PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(Farmer_App_2Application.class, args);
	}

}
