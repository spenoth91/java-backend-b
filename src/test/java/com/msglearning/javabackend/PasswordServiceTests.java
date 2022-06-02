package com.msglearning.javabackend;

import com.msglearning.javabackend.services.PasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PasswordServiceTests {

    @Test
    void passwordHashFunctionReturnTest() throws Exception {

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