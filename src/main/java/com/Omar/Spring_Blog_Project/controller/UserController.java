package com.Omar.Spring_Blog_Project.controller;



import com.Omar.Spring_Blog_Project.dto.AuthResponse;
import com.Omar.Spring_Blog_Project.dto.LoginRequest;
import com.Omar.Spring_Blog_Project.exception.EmailAlreadyExistsException;
import com.Omar.Spring_Blog_Project.exception.NotFoundException;
import com.Omar.Spring_Blog_Project.exception.UnauthorizedException;
import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.service.JwtService;
import com.Omar.Spring_Blog_Project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody User user) {

        try {

            if(user.getFirstName() == null || user.getPassword() == null || user.getFirstName().isBlank() || user.getLastName().isBlank()) {
                throw new NotFoundException("Incorrect username or Password");
            }

            if(userService.checkEmailExists(user.getEmail())) {
                throw new EmailAlreadyExistsException("Email already registered");
            }

            String token = jwtService.generateToken(user);

            userService.saveUser(user);

            return ResponseEntity.ok(new AuthResponse(token));
        }

        catch (BadCredentialsException ex) {
            throw new UnauthorizedException(ex.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );


            User user = userService.getUserByEmail(request.email());
            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException(ex.getMessage());
        }
    }

}
