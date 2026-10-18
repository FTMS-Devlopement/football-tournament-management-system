
package com.ftms.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftms.user.entity.UserRole;
import com.ftms.user.entity.UserRoleId;

public interface UserRoleRepository
        extends JpaRepository<UserRole, UserRoleId> {

    List<UserRole> findByUserId(Long userId);
}
