package com.example.appbanhangonlinereal.activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddProductAdminActivityTest {
    @Test
    public void testValidInputs() {
        String result = AddProductAdminActivity.isValidInsert("Product Name", "1000", "Product Description");
        assertEquals("Valid", result);
    }

    @Test
    public void testInvalidNameEmpty() {
        String result = AddProductAdminActivity.isValidInsert("", "100", "Product Description");
        assertEquals("Invalid name product", result);
    }

    @Test
    public void testInvalidNameStartsWithDigit() {
        String result = AddProductAdminActivity.isValidInsert("1Product", "100", "Product Description");
        assertEquals("Invalid name product", result);
    }

    @Test
    public void testInvalidNameSpecialCharacters() {
        String result = AddProductAdminActivity.isValidInsert("Product@123", "100", "Product Description");
        assertEquals("Invalid name product", result);
    }

    @Test
    public void testInvalidPriceEmpty() {
        String result = AddProductAdminActivity.isValidInsert("Product Name", "", "Product Description");
        assertEquals("Invalid price product", result);
    }

    @Test
    public void testInvalidPriceNegative() {
        String result = AddProductAdminActivity.isValidInsert("Product Name", "-100", "Product Description");
        assertEquals("Invalid price product", result);
    }

    @Test
    public void testInvalidDescribeEmpty() {
        String result = AddProductAdminActivity.isValidInsert("Product Name", "1000", "");
        assertEquals("Invalid describe product", result);
    }
}