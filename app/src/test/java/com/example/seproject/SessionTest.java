package com.example.seproject;

import com.example.seproject.models.Session;

import org.junit.Test;
import static org.junit.Assert.*;

public class SessionTest {
    @Test
    public void testSessionCreation() {
        Session session = new Session(
                "s1",
                "student1",
                "tutor1",
                "2026-04-10",
                "accepted"
        );

        assertEquals("s1", session.getSessionId());
        assertEquals("student1", session.getStudentId());
        assertEquals("tutor1", session.getTutorId());
        assertEquals("2026-04-10", session.getDate());
        assertEquals("accepted", session.getStatus());
        assertEquals(0, session.getRating());
        assertEquals("", session.getReviewComment());
    }
}
