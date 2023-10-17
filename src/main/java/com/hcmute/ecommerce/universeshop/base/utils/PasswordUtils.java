package com.hcmute.ecommerce.universeshop.base.utils;

import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@NoArgsConstructor
public final class PasswordUtils {
    public static final String SPECIAL_CHARACTER_VALIDATOR = "^(?=.*[@$!%*?&^#\\{}\\[\\]`~\\(\\)\\|\"';:\\/.,<>_\\+=\\\\-])[A-Za-z\\d@$!%*?&^#\\{}\\[\\]`~\\(\\)\\|\"';:\\/.,<>_\\+=\\\\-]*$";
    public static Boolean isPasswordValid(String inputPassword) {
        String whiteSpace = " ";
        if(inputPassword.contains(whiteSpace)) {
            return false;
        }
        if(inputPassword.length() < 12) {
            return false;
        }
        int numberPoint = 0;
        int uppercasePoint =0;
        int lowercasePoint = 0;
        int specialCharacterPoint = 0;
        for(int i = 0; i < inputPassword.length(); i++) {
            if(Character.isDigit(inputPassword.charAt(i))) {
                numberPoint = 1;
            }
            if(Character.isUpperCase(inputPassword.charAt(i))) {
                uppercasePoint = 1;
            }
            if(Character.isDigit(inputPassword.charAt(i))) {
                numberPoint = 1;
            }
            if(numberPoint + uppercasePoint + lowercasePoint == 3) return true;
        }
        Pattern specialCharacterPattern = Pattern.compile(SPECIAL_CHARACTER_VALIDATOR);
        Matcher specialCharacterChecker = specialCharacterPattern.matcher(inputPassword);
        if(specialCharacterChecker.find()) {
            specialCharacterPoint = 1;
        }
        return numberPoint + lowercasePoint + uppercasePoint + specialCharacterPoint == 3;
    }
}
