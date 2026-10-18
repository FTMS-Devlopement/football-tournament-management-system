
package com.ftms.user.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftms.user.dto.LoginRequest;
import com.ftms.user.dto.RegisterRequest;
import com.ftms.user.dto.UserResponse;
import com.ftms.user.entity.UserAccount;
import com.ftms.user.entity.UserRole;
import com.ftms.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {

		// Public registration must not allow ADMIN role assignment.
		UserRole.Role role = request.getRole();

		if (role == null || role == UserRole.Role.ADMIN) {
			return ResponseEntity.badRequest().body("Choose a valid, non-admin role");
		}

		UserAccount user = userService.registerUser(request.getFullName(), request.getEmail(), request.getPassword(),
				role);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("User registered successfully. User ID: " + user.getUserId());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Long userId) {

		try {
			return ResponseEntity.ok(userService.getUserById(userId));
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
	}

	@GetMapping("/registration-count")
	public ResponseEntity<Long> getRegistrationCount() {
		return ResponseEntity.ok(userService.getRegistrationCount());
	}

@GetMapping("/all")
public ResponseEntity<List<UserResponse>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
}

@GetMapping("/{userId}/roles")
public ResponseEntity<?> getUserRoles(
        @PathVariable Long userId) {

    try {
        return ResponseEntity.ok(userService.getUserRoles(userId));
    } catch (IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}

@PostMapping("/login")
public ResponseEntity<?> login(
        @Valid @RequestBody LoginRequest request) {

    try {
        return ResponseEntity.ok(
                userService.loginUser(
                        request.getEmail(),
                        request.getPassword()
                )
        );
    } catch (IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
}


}
