package com.example.seproject;

import com.example.seproject.models.Request;
import com.example.seproject.models.Session;

import org.junit.Test;
import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void testRequestCreation() {
        Request request = new Request(
                "req1",
                "student1",
                "tutor1",
                "Need help",
                "2026-10-04",
                "pending"
        );

        assertEquals("req1", request.getRequestId());
        assertEquals("student1", request.getStudentId());
        assertEquals("tutor1", request.getTutorId());
        assertEquals("Need help", request.getMessage());
        assertEquals("pending", request.getStatus());
        assertEquals(10, request.getCreditAmount());
    }

    @Test
    public void testStatusUpdate() {
        Request request = new Request();

        request.setStatus("pending");
        request.setStatus("accepted");

        assertEquals("accepted", request.getStatus());
    }

    @Test
    public void testRequestAcceptFlow() {
        Request request = new Request(
                "1",
                "student1",
                "tutor1",
                "Help needed",
                "2026-10-04",
                "pending"
        );

        // simulate accept
        request.setStatus("accepted");

        Session session = new Session(
                "s1",
                request.getStudentId(),
                request.getTutorId(),
                request.getDate(),
                request.getStatus()
        );

        assertEquals("accepted", session.getStatus());
    }

    @Test
    public void testRequestWithExplicitCreditAmount() {
        Request request = new Request(
                "req2",
                "student2",
                "tutor2",
                "Physics help",
                "2026-11-01",
                "pending",
                25
        );

        assertEquals(25, request.getCreditAmount());
    }
}
