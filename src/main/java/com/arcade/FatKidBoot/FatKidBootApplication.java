package com.arcade.FatKidBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FatKidBootApplication {

	static void main(String[] args) {
		SpringApplication.run(FatKidBootApplication.class, args);
	}

}
