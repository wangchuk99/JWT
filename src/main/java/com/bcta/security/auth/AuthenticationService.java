package com.bcta.security.auth;

import com.bcta.security.config.JWTService;
import com.bcta.security.user.Role;
import com.bcta.security.user.User;
import com.bcta.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;


    // Method logics for registration of a user to db
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .id(request.getId())
                .username(request.getUsername())
                .fullName(request.getFullName())

                // encrypts the password when saving it to db
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .contactNumber(request.getContactNumber())
                .status(request.getStatus())
                .rememberToken(request.getRememberToken())
                .cmnProcuringAgencyId(request.getCmnProcuringAgencyId())
                .createdBy(request.getCreatedBy())
                .editedBy(request.getEditedBy())
                .role(Role.USER)
                .build();
        repository.save(user);

        // Generates JWT with the user information sent by client during registration
        var jwtToken = jwtService.generateJWT(user);

        // Returns the generated token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // Method to authenticate the client user with the data from db
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Gets the user details from db
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        //Generates the JWT once authenticated
        var jwtToken = jwtService.generateJWT(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
