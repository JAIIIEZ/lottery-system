package com.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.dto.UserDto;
import com.spring.exception.UserAlreadyExistException;
import com.spring.model.User;
import com.spring.service.UserService;

import lombok.SneakyThrows;

@Component
public class UserValidator implements Validator
{
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @SneakyThrows
    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if (userService.findByUsername(userDto.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
            throw new UserAlreadyExistException("user already exist :: " + userDto.getUsername());
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userDto.getPassword().length() < 6 || userDto.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!userDto.getPasswordConfirm().equals(userDto.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}