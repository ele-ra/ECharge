package com.echarge.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    public ERole role;

    @Override
    public String getAuthority() {
        return "ROLE_" + role.getAuthority();
    }

    public String getRoleName() {
        return role.name();
    }

    public enum ERole {
        ADMIN(1),
        CUSTOMER(2);
        private final int value;

        public boolean isAdmin() {
            return ((this.value & 1) != 0);
        }

        ERole(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getAuthority() {
            return "ROLE_" + (isAdmin() ? "ADMIN" : "USER");
        }
    }
}
