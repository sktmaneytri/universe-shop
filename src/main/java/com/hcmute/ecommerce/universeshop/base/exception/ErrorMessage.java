package com.hcmute.ecommerce.universeshop.base.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ErrorMessage {
    private final MessageSource messageSource;

    public ErrorMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

}
