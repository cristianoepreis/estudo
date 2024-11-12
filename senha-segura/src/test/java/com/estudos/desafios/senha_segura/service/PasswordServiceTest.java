package com.estudos.desafios.senha_segura.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordServiceTest {

    private final PasswordService passwordService = new PasswordService();

    @Test
    void testValidatePass_AllConditionsMet() {
        String pass = "Abcdef1@";
        List<String> failures = passwordService.validatePass(pass);
        assertTrue(failures.isEmpty(), "Expected no failures, but got: " + failures);
    }

    @Test
    void testValidatePass_LengthFailure() {
        String pass = "Ab1@";
        List<String> failures = passwordService.validatePass(pass);
        assertEquals(1, failures.size());
        assertEquals("A senha deve ter no mínimo 8 caracteres.", failures.get(0));
    }

    @Test
    void testValidatePass_UpperCaseFailure() {
        String pass = "abcdef1@";
        List<String> failures = passwordService.validatePass(pass);
        assertEquals(1, failures.size());
        assertEquals("A senha deve conter no mínimo 1 letra maiúscula.", failures.get(0));
    }

    @Test
    void testValidatePass_LowerCaseFailure() {
        String pass = "ABCDEF1@";
        List<String> failures = passwordService.validatePass(pass);
        assertEquals(1, failures.size());
        assertEquals("A senha deve conter no mínimo 1 letra minúscula.", failures.get(0));
    }

    @Test
    void testValidatePass_NumberFailure() {
        String pass = "Abcdef@#";
        List<String> failures = passwordService.validatePass(pass);
        assertEquals(1, failures.size());
        assertEquals("A senha deve conter no mínimo 1 número.", failures.get(0));
    }

    @Test
    void testValidatePass_SpecialCharFailure() {
        String pass = "Abcdef12";
        List<String> failures = passwordService.validatePass(pass);
        assertEquals(1, failures.size());
        assertEquals("A senha deve conter no mínimo 1 caractere especial.", failures.get(0));
    }

    @Test
    void testValidatePass_MultipleFailures() {
        String pass = "abcde";
        List<String> failures = passwordService.validatePass(pass);
        assertEquals(4, failures.size());
        assertTrue(failures.contains("A senha deve ter no mínimo 8 caracteres."));
        assertTrue(failures.contains("A senha deve conter no mínimo 1 letra maiúscula."));
        assertTrue(failures.contains("A senha deve conter no mínimo 1 número."));
        assertTrue(failures.contains("A senha deve conter no mínimo 1 caractere especial."));
    }
}