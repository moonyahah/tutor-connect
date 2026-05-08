package com.example.seproject;

import com.example.seproject.models.*;

import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests normal user flows and system behavior.
 */
public class UserFlowTests {

    @Test
    public void testUserSignupAndData() {
        User user = new User("1", "Ali", "ali@test.com", "student");

        assertEquals("Ali", user.getName());
        assertEquals("student", user.getRole());
    }

    @Test
    public void testTutorProfileSetup() {
        TutorProfile tutor = new TutorProfile(
                "1",
                Arrays.asList("Math", "Physics"),
                "Experienced tutor"
        );

        assertTrue(tutor.getSubjects().contains("Math"));
        assertEquals("Experienced tutor", tutor.getBio());
    }

    @Test
    public void testRequestCreationFlow() {
        Request request = new Request(
                "r1",
                "student1",
                "tutor1",
                "Need help with calculus",
                "2026-10-04",
                "pending"
        );

        assertEquals("pending", request.getStatus());
    }

    @Test
    public void testRequestAcceptanceFlow() {
        Request request = new Request(
                "r1",
                "student1",
                "tutor1",
                "Help",
                "2026-10-04",
                "pending"
        );

        request.setStatus("accepted");

        assertEquals("accepted", request.getStatus());
    }

    @Test
    public void testRequestToSessionFlow() {
        Request request = new Request(
                "r1",
                "student1",
                "tutor1",
                "Help",
                "2026-04-10",
                "accepted"
        );

        Session session = new Session(
                "s1",
                request.getStudentId(),
                request.getTutorId(),
                "2026-04-10",
                request.getStatus()
        );

        assertEquals("student1", session.getStudentId());
        assertEquals("tutor1", session.getTutorId());
        assertEquals("accepted", session.getStatus());
    }

    @Test
    public void testSessionDisplayFlow() {
        Session session = new Session(
                "s1",
                "student1",
                "tutor1",
                "2026-04-10",
                "accepted"
        );

        assertTrue(session.getDate().contains("2026"));
    }

    @Test
    public void testTutorSearchFlow() {
        TutorProfile tutor = new TutorProfile(
                "1",
                Arrays.asList("Math", "CS"),
                "Expert"
        );

        boolean found = false;

        for (String subject : tutor.getSubjects()) {
            if (subject.equalsIgnoreCase("math")) {
                found = true;
            }
        }

        assertTrue(found);
    }
}