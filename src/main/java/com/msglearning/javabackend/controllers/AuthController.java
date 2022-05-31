package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.services.TokenService;
import com.msglearning.javabackend.services.UserService;
import com.msglearning.javabackend.to.UserTO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_AUTH })
public class AuthController {
    public static final String AUTHORIZATION = "authorization";
    private static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping(LOGIN_PATH)
    public String login(@RequestHeader Map<String, String> headers) {

        String decodedAuthHeader;
        try {
            decodedAuthHeader = new String(Base64.decodeBase64(
                    headers.get(AUTHORIZATION).substring(6)
            ));
        }
        catch (NullPointerException e) {
            return "Forbidden";
        }
        String email = decodedAuthHeader.substring(0, decodedAuthHeader.indexOf(":"));
        String password = decodedAuthHeader.substring(decodedAuthHeader.indexOf(":") + 1);

        if (!StringUtils.hasLength(email) || !StringUtils.hasLength(password)) {
            return "Forbidden";
        }

        Optional<User> userOptional = this.userService.findByEmail(email);
        if (userOptional.isPresent()) {
            return this.tokenService.createTokenHeader(
                    userOptional.get().getEmail()
            );
        }

        return "Forbidden";
    }

    @PostMapping(REGISTER_PATH)
    public boolean register(@RequestBody UserTO userTO) {
        try {
            userService.save(userTO);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
