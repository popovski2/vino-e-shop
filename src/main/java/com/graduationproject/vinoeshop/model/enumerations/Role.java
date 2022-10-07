package com.graduationproject.vinoeshop.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_CUSTOMER, ROLE_MANUFACTURER;

    @Override
    public String getAuthority() {
        return name();
    }
}
