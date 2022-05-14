package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.to.UserTO;

public class UserConverter {

    public static final UserTO convertToTO(User entity) {
        return new UserTO(entity.getId(), entity.getFirstName(),
                entity.getLastName(), entity.getEmail(), entity.getPhone(), entity.getPassword());
    }

    public static final User convertToEntity(UserTO userTO) {
        User user = new User();
        user.setId(userTO.getId());
        user.setEmail(userTO.getEmail());
        user.setFirstName(userTO.getFirstName());
        user.setLastName(userTO.getLastName());
        user.setPhone(userTO.getPhone());
        user.setPassword(userTO.getPassword());
        return user;
    }

}
