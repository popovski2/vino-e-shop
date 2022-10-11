package com.graduationproject.vinoeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@SpringBootApplication
public class VinoEShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(VinoEShopApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws UnsupportedEncodingException {
        return new BCryptPasswordEncoder(11, new SecureRandom("seed".getBytes("UTF-8")));
    }

}
