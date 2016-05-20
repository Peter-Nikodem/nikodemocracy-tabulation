package net.nikodem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.*;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class NikodemocracyTabulationApplication {
	public static void main(String[] args) {
		SpringApplication.run(NikodemocracyTabulationApplication.class, args);
	}
}
