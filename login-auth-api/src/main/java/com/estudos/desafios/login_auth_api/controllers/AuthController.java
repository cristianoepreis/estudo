package com.estudos.desafios.login_auth_api.controllers;

import com.estudos.desafios.login_auth_api.domain.user.User;
import com.estudos.desafios.login_auth_api.dto.LoginRequestDTO;
import com.estudos.desafios.login_auth_api.dto.RegisterRequestDTO;
import com.estudos.desafios.login_auth_api.dto.ResponseDTO;
import com.estudos.desafios.login_auth_api.infra.security.TokenService;
import com.estudos.desafios.login_auth_api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody LoginRequestDTO body) {
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuário não Encontrado"));
        if (this.passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.gerarToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(),token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity efetuarRegister(@RequestBody RegisterRequestDTO body) {

        Optional<User> user = this.userRepository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.userRepository.save(newUser);

            String token = this.tokenService.gerarToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
            }
        return ResponseEntity.badRequest().build();
    }
}
