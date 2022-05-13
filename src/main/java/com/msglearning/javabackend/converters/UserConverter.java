package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.to.UserTO;

public class UserConverter {

    public static final UserTO convertToTO(User entity) {
        return new UserTO(entity.getId(), entity.getFirstName(),
                entity.getLastName(), entity.getEmail(), entity.getPhone());
    }

    public static final User convertFromTO(UserTO entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setPhone(entity.getPhone());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        return user;
    }

}
