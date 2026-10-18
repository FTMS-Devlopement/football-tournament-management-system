
package com.ftms.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_role")
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public UserRole() {
    }

    public UserRole(Long userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        ADMIN,
        PLAYER,
        TEAM_MANAGER,
        MATCH_OFFICIAL
    }
}
