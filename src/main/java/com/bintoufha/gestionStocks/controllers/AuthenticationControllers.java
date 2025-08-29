package com.bintoufha.gestionStocks.controllers;


import com.bintoufha.gestionStocks.dto.auth.AuthenticationRequest;
import com.bintoufha.gestionStocks.dto.auth.AuthenticationResponse;
import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.model.auth.ExtendedUser;
import com.bintoufha.gestionStocks.services.auth.ApplicationUserDetailsService;
import com.bintoufha.gestionStocks.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bintoufha.gestionStocks.utils.Constante.AUTHENTICATE_ENDPOINT;
//@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthenticationControllers {

    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  ApplicationUserDetailsService userDetailsService;
    @Autowired
    private  JwtUtils jwtUtils;

    @PostMapping(AUTHENTICATE_ENDPOINT)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            final String token = jwtUtils.generateToken((ExtendedUser) userDetails);
            return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(token).build());
        } catch (BadCredentialsException ex) {
            return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
                    .body(AuthenticationResponse.builder()
                            .message("Identifiants invalides")
                            .build());
        }
    }
}
