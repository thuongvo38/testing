package com.example.appbanhangonlinereal.activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegisterActivityTest {
    @Test
    public void testValidPassword() {
        assertTrue(RegisterActivity.isValidPass("ValidPass123@"));
    }

    @Test
    public void testShortPassword() {
        assertFalse(RegisterActivity.isValidPass("Short1$"));
    }

    @Test
    public void testPasswordWithoutLetter() {
        assertFalse(RegisterActivity.isValidPass("12345678$"));
    }

    @Test
    public void testPasswordWithoutDigit() {
        assertFalse(RegisterActivity.isValidPass("NoDigit@Symbol"));
    }

    @Test
    public void testValidUsername() {
        assertTrue(RegisterActivity.isValidUser("ValidUser"));
    }

    @Test
    public void testUsernameTooLong() {
        assertFalse(RegisterActivity.isValidUser("TooLongUsername123"));
    }

    @Test
    public void testUsernameStartsWithNumber() {
        assertFalse(RegisterActivity.isValidUser("1InvalidUsername"));
    }

    @Test
    public void testUsernameStartsWithSpecialChar() {
        assertFalse(RegisterActivity.isValidUser("!InvalidUsername"));
    }

    @Test
    public void testUsernameWithBlankInside() {
        assertFalse(RegisterActivity.isValidUser("Invalid User"));
    }
}