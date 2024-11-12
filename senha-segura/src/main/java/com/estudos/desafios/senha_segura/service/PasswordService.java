package com.estudos.desafios.senha_segura.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PasswordService {

    public List<String> validatePass(String pass) {
        List<String> failures = new ArrayList<>();

        valitadeLength(pass, failures);
        validateUpperCase(pass, failures);
        validateLowerCase(pass, failures);
        validateNumber(pass, failures);
        validateSpecialChar(pass, failures);

        return failures;
    }

    private void valitadeLength(String pass, List<String> failures) {
        if (pass.length() < 8) {
            failures.add("A senha deve ter no mínimo 8 caracteres.");
        }
    }

    private void validateUpperCase(String pass, List<String> failures) {
        if (pass.equals(pass.toLowerCase())) {
            failures.add("A senha deve conter no mínimo 1 letra maiúscula.");
        }
    }

    private void validateLowerCase(String pass, List<String> failures) {
        if (pass.equals(pass.toUpperCase())) {
            failures.add("A senha deve conter no mínimo 1 letra minúscula.");
        }
    }

    private void validateNumber(String pass, List<String> failures) {
        if (!pass.matches(".*\\d.*")) {
            failures.add("A senha deve conter no mínimo 1 número.");
        }
    }

    private void validateSpecialChar(String pass, List<String> failures) {
        if (!pass.matches(".*\\W.*")) {
            failures.add("A senha deve conter no mínimo 1 caractere especial.");
        }
    }
}
