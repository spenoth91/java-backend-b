package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.to.UserTO;

public class UserConverter {

    public static final UserTO convertToTO(User entity) {
        return new UserTO(entity.getId(), entity.getFirstName(),
                entity.getLastName(), entity.getEmail(), entity.getPhone(), entity.getPassword());
    }

    public static final User convertFromTO(UserTO entity) {
        return new User(entity.getId(), entity.getPassword(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhone());
    }

}
