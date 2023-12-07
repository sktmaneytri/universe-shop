package com.hcmute.ecommerce.universeshop.base.email;

import java.util.Map;

public interface ThymeleafService {
    String createContentGetOTP(String template, Map<String, Object> variables);
}
