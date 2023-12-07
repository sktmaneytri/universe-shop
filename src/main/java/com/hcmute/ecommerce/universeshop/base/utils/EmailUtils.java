package com.hcmute.ecommerce.universeshop.base.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@Component
public final class EmailUtils {
    public static final String EMAIL_VALIDATOR = "^[a-zA-Z]+[0-9a-zA-Z._]*@[a-zA-Z0-9]{3,10}(\\.[a-zA-Z]+){1,3}$";
    public static Boolean isEmailValid(String inputEmail) {
        Pattern emailPattern = Pattern.compile(EMAIL_VALIDATOR);
        Matcher emailChecker = emailPattern.matcher(inputEmail);

        return emailChecker.find();
    }

}