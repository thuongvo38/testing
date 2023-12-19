package com.example.appbanhangonlinereal.activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginActivityTest {
    @Test
    public void testValidInput() {
        assertEquals(11, LoginActivity.isValidLoginInput("test@gmail.com", "ValidPass123@"));
    }

    @Test
    public void testNoInput() {
        assertEquals(0, LoginActivity.isValidLoginInput("", ""));
    }

    @Test
    public void testNoEmail() {
        assertEquals(1, LoginActivity.isValidLoginInput("", "ValidPass123@"));
    }

    @Test
    public void testNoPassword() {
        assertEquals(2, LoginActivity.isValidLoginInput("test@gmail.com", ""));
    }

    @Test
    public void testInvalidPassword() {
        assertEquals(21, LoginActivity.isValidLoginInput("test@gmail.com", "invalid"));
    }


}