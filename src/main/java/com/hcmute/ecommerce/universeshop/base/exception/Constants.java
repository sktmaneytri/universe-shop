package com.hcmute.ecommerce.universeshop.base.exception;

public class Constants {
    // Input validation exception
    public static final String INVALID_INPUT_VALUE = "exception.input.validation";

    // Resource not found exception
    public static final String PRODUCT_NOT_FOUND = "exception.resource-not-found-product";
    public static final String TAG_NOT_FOUND = "exception.resource-not-found-tag";
    public static final String USER_NOT_FOUND = "exception.resource-not-found-user";
    public static final String ROLE_NOT_FOUND = "exception.resource-not-found-role";
    public static final String POST_TAG_NOT_FOUND = "exception.resource-not-found-post-tag";

    // Security exception
    public static final String TAG_NAME_EXISTED = "exception.security.tag-name-existed";
    public static final String POST_TITLE_EXISTED = "exception.security.post-title-existed";

    public static final String ROLE_NAME_EXISTED = "exception.security.role-existed";
    public static final String USER_EMAIL_INVALID = "exception.security.user-email-invalid";
    public static final String USER_PASSWORD_INVALID = "exception.security.user-password-invalid";
    public static final String USERNAME_EXISTED = "exception.security.username-existed";
    public static final String USER_EMAIL_EXISTED = "exception.security.user-email-existed";
    public static final String UNAUTHORIZED_ACCESS ="exception.security.unauthorized-access";
    public static final String CONFIRM_PASSWORD_NOT_MATCH = "exception.security.confirm-password-not-match";

    // System exception
    public static final String CONTENT_IS_BLANK = "exception.system.content-is-blank";
    public static final String CONTENT_MUST_NOT_BE_NULL = "exception.system.content-must-not-be-null";

//    public static final String TOKEN_IS_INVALID = "Invalid token!";
//    public static final String USERNAME_IS_INVALID = "Invalid username!";
 public static final String PRODUCT_NAME_EXISTED = "exception.security.product-name-existed";
    public static final String CATEGORY_NOT_FOUND = "exception.security.category-not-found";
    public static final String CART_NOT_FOUND = "exception.security.cart-not-found";
    public static final String CATEGORY_NAME_EXISTED = "exception.security.category-name-existed";

}