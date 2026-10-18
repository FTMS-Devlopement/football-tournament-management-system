
package com.ftms.user.dto;

import java.util.List;

import com.ftms.user.entity.UserAccount;
import com.ftms.user.entity.UserRole;

public class UserResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String status;
    private List<String> roles;

    public UserResponse() {
    }

    public UserResponse(
            Long userId,
            String fullName,
            String email,
            String status,
            List<String> roles) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getRoles() {
        return roles;
    }
}
