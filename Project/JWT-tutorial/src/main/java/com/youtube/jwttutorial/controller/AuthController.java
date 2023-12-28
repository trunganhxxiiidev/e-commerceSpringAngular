package com.youtube.jwttutorial.controller;

import com.youtube.jwttutorial.Security.JwtUtils;
import com.youtube.jwttutorial.Security.Services.UserDetailsImpl;
import com.youtube.jwttutorial.dao.RoleDao;
import com.youtube.jwttutorial.dao.UserDao;
import com.youtube.jwttutorial.entity.Role;
import com.youtube.jwttutorial.entity.User;
import com.youtube.jwttutorial.payload.request.LoginRequest;
import com.youtube.jwttutorial.payload.request.SignupRequest;
import com.youtube.jwttutorial.payload.response.MessageResponse;
import com.youtube.jwttutorial.payload.response.UserInfoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        System.out.println(jwtCookie);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println(roles);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(
                        userDetails.getUsername(),
                        userDetails.getUserFirstName(),
                        userDetails.getUserLastName(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userDao.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getUserFirstName(),
                signUpRequest.getUserLastName(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleDao.findByRoleName("user")
                    .orElseThrow(() -> new RuntimeException("Error:user Role is not found."));
            roles.add(userRole);

        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleDao.findByRoleName("admin")
                                .orElseThrow(() -> new RuntimeException("Error:Admin Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleDao.findByRoleName("mod")
                                .orElseThrow(() -> new RuntimeException("Error:Mod Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleDao.findByRoleName("user")
                                .orElseThrow(() -> new RuntimeException("Error:User Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRole(roles);
        userDao.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
