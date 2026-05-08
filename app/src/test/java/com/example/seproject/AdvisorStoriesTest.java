package com.example.seproject;

import com.example.seproject.models.AdvisorReviewItem;
import com.example.seproject.models.AdvisorStudentStat;
import com.example.seproject.models.AdvisorTutorStat;
import com.example.seproject.models.Request;
import com.example.seproject.models.Session;
import com.example.seproject.models.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdvisorStoriesTest {

    @Test
    public void testAdvisorRoleUserCreation() {
        User advisor = new User("advisor1", "Advisor A", "advisor@test.com", "advisor");

        assertEquals("advisor", advisor.getRole());
        assertEquals("Advisor A", advisor.getName());
    }

    @Test
    public void testFrequentStudentStatModel() {
        AdvisorStudentStat stat = new AdvisorStudentStat("student1", "Ali", 5);

        assertEquals("student1", stat.getStudentId());
        assertEquals("Ali", stat.getStudentName());
        assertEquals(5, stat.getRequestCount());
    }

    @Test
    public void testTutorRatingStatModel() {
        AdvisorTutorStat stat = new AdvisorTutorStat("tutor1", "Sara", 4.7, 12);

        assertEquals("tutor1", stat.getTutorId());
        assertEquals("Sara", stat.getTutorName());
        assertEquals(4.7, stat.getAverageRating(), 0.001);
        assertEquals(12, stat.getReviewCount());
    }

    @Test
    public void testReviewModerationFlagModel() {
        AdvisorReviewItem item = new AdvisorReviewItem(
                "session1",
                "tutor1",
                "student1",
                "Tutor Name",
                "Student Name",
                1,
                "Inappropriate behavior",
                false
        );

        assertFalse(item.isFlagged());
        item.setFlagged(true);
        assertTrue(item.isFlagged());
    }

    @Test
    public void testSessionDefaultsForModeration() {
        Session session = new Session("s1", "student1", "tutor1", "2026-04-25", "accepted");

        assertFalse(session.isFlagged());
        assertEquals(0, session.getRating());
        assertEquals("", session.getReviewComment());
    }

    @Test
    public void testRequestCreditDefaultsForAdvisorAnalytics() {
        Request request = new Request("r1", "student1", "tutor1", "Need help", "2026-05-10", "pending");

        assertEquals(10, request.getCreditAmount());
    }
}
