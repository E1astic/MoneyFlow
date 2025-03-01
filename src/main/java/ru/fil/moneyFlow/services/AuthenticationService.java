package ru.fil.moneyFlow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fil.moneyFlow.config.JwtService;
import ru.fil.moneyFlow.models.Role;
import ru.fil.moneyFlow.models.User;
import ru.fil.moneyFlow.repositories.UserRepository;
import ru.fil.moneyFlow.dto.AuthenticationRequest;
import ru.fil.moneyFlow.dto.AuthenticationResponse;
import ru.fil.moneyFlow.dto.RegisterRequest;
import ru.fil.moneyFlow.utils.AuthException;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional()
    public AuthenticationResponse register(RegisterRequest request) {
        User user=convertToUser(request);
        userRepository.save(user);
        String jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .build();
        }
        catch(BadCredentialsException e) {
            throw new AuthException("Bad credentials");
        }
    }


    private User convertToUser(RegisterRequest request) {
        return User
                .builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }
}
