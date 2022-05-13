package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.UserConverter;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.repositories.UserRepository;
import com.msglearning.javabackend.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {

        //validate Phone
        if ( !validatePhoneNumber(user.getPhone()) ){
            return null;
        }
        //validate email
        if ( !validateEmail(user.getEmail()) ){
            return null;
        }
        //check firstname NotNull or empty
        if ( !validateName(user.getFirstName()) ){
            return null;
        }
        //check lastName NotNull or empty
        if ( !validateName(user.getLastName()) ){
            return null;
        }
        //validate password
        if( !validatePassword(user.getPassword()) ){
            return null;
        }

        return userRepository.save(user);
    }

    public List<UserTO> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            return Collections.emptyList();
        else
            return users.stream()
                    .map(UserConverter::convertToTO)
                    .collect(Collectors.toList());
    }

    public List<UserTO> findByName(String token) {
        List<User> users = userRepository.findByName(token);
        if (users.isEmpty())
            return Collections.emptyList();
        else
            return users.stream()
                    .map(UserConverter::convertToTO)
                    .collect(Collectors.toList());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<String> getProfileImage(Long userId) {
        return userRepository.findProfileImageById(userId);
    }

    private Boolean validateEmail(String email){
        return findByEmail(email).isEmpty();
    }

    private Boolean validateName(String name){
        return !(name == null || name.length() == 0);
    }

    private Boolean validatePassword(String password){
        return password != null;
    }

    private Boolean validatePhoneNumber(String phoneNumber){
        return  phoneNumber.matches( "(?:\\+40)?0?\\d{9}" );
    }

}
