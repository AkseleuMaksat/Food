package com.food;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGeneratorTest {

    @Test
    public void generatePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "qwerty";
        String hash = encoder.encode(password);
        System.out.println("GENERATED HASH FOR " + password + ": " + hash);
    }
}
