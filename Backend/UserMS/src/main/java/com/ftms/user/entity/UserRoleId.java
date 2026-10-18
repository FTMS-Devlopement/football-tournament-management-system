
package com.ftms.user.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {

    private Long userId;
    private UserRole.Role role;

    public UserRoleId() {
    }

    public UserRoleId(Long userId, UserRole.Role role) {
        this.userId = userId;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof UserRoleId)) {
            return false;
        }

        UserRoleId other = (UserRoleId) obj;

        return Objects.equals(userId, other.userId)
                && Objects.equals(role, other.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, role);
    }
}
