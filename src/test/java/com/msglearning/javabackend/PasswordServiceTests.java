package com.msglearning.javabackend;

import com.msglearning.javabackend.services.PasswordService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PasswordServiceTests {

    @Test
    void passwordHashFunctionReturnTest() {

        // initial test password
        String password = "iliketosleep123";

        // hashing the password
        String hashedPassword = PasswordService.hashPassword(password);

        // check if not null
        assertThat(hashedPassword).isNotEmpty();

        // check if it's not returning the original
        assertThat(hashedPassword).isNotEqualTo(password);

        // to check
        System.out.println("passwordHashFunctionReturnTest()::hashed password = " + hashedPassword);
    }
}