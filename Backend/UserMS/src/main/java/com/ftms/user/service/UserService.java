
package com.ftms.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftms.user.dto.UserResponse;
import com.ftms.user.entity.UserAccount;
import com.ftms.user.entity.UserRole;
import com.ftms.user.repository.UserAccountRepository;
import com.ftms.user.repository.UserRoleRepository;

@Service
public class UserService {

	private final UserAccountRepository userAccountRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserAccountRepository userAccountRepository, UserRoleRepository userRoleRepository,
			PasswordEncoder passwordEncoder) {
		this.userAccountRepository = userAccountRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserAccount registerUser(String fullName, String email, String password, UserRole.Role role) {

		if (userAccountRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("An account with this email already exists");
		}

		UserAccount user = new UserAccount();
		user.setFullName(fullName);
		user.setEmail(email);
		user.setPasswordHash(passwordEncoder.encode(password));
		user.setStatus(UserAccount.AccountStatus.ACTIVE);

		UserAccount savedUser = userAccountRepository.save(user);

		UserRole userRole = new UserRole(savedUser.getUserId(), role);
		userRoleRepository.save(userRole);

		return savedUser;
	}

	public UserResponse getUserById(Long userId) {

		UserAccount user = userAccountRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		List<String> roles = userRoleRepository.findByUserId(userId).stream().map(userRole -> userRole.getRole().name())
				.toList();

		return new UserResponse(user.getUserId(), user.getFullName(), user.getEmail(), user.getStatus().name(), roles);
	}

	public long getRegistrationCount() {
		return userAccountRepository.count();
	}

public List<UserResponse> getAllUsers() {

    List<UserAccount> users = userAccountRepository.findAll();

    List<UserResponse> responses = new ArrayList<>();

    for (UserAccount user : users) {

        List<String> roles = userRoleRepository
                .findByUserId(user.getUserId())
                .stream()
                .map(userRole -> userRole.getRole().name())
                .toList();

        responses.add(new UserResponse(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getStatus().name(),
                roles
        ));
    }

    return responses;
}

public List<String> getUserRoles(Long userId) {

    if (!userAccountRepository.existsById(userId)) {
        throw new IllegalArgumentException("User not found");
    }

    return userRoleRepository.findByUserId(userId)
            .stream()
            .map(userRole -> userRole.getRole().name())
            .toList();
}

public UserResponse loginUser(String email, String password) {

    UserAccount user = userAccountRepository.findByEmail(email)
            .orElseThrow(() ->
                    new IllegalArgumentException("Invalid email or password"));

    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
        throw new IllegalArgumentException("Invalid email or password");
    }

    if (user.getStatus() != UserAccount.AccountStatus.ACTIVE) {
        throw new IllegalArgumentException("Account is disabled");
    }

    List<String> roles = userRoleRepository.findByUserId(user.getUserId())
            .stream()
            .map(userRole -> userRole.getRole().name())
            .toList();

    return new UserResponse(
            user.getUserId(),
            user.getFullName(),
            user.getEmail(),
            user.getStatus().name(),
            roles
    );
}


}
