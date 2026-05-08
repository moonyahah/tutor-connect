package com.example.seproject;

import com.example.seproject.models.User;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("1", "Ali", "ali@test.com", "student");

        assertEquals("1", user.getId());
        assertEquals("Ali", user.getName());
        assertEquals("ali@test.com", user.getEmail());
        assertEquals("student", user.getRole());
    assertEquals(100, user.getCredits());
    }

    @Test
    public void testSetters() {
        User user = new User();

        user.setId("2");
        user.setName("Ahmed");
        user.setEmail("ahmed@test.com");
        user.setRole("tutor");
    user.setCredits(150);

        assertEquals("2", user.getId());
        assertEquals("Ahmed", user.getName());
        assertEquals("ahmed@test.com", user.getEmail());
        assertEquals("tutor", user.getRole());
        assertEquals(150, user.getCredits());
    }
}
