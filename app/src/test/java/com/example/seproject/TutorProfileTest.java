package com.example.seproject;
import com.example.seproject.models.TutorProfile;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class TutorProfileTest {

    @Test
    public void testTutorProfileCreation() {
        TutorProfile profile = new TutorProfile(
                "1",
                Arrays.asList("Math", "CS"),
                "Expert tutor"
        );

        assertEquals("1", profile.getUserId());
        assertEquals(2, profile.getSubjects().size());
        assertEquals("Expert tutor", profile.getBio());
        assertEquals(0.0, profile.getAverageRating(), 0.001);
        assertEquals(0, profile.getReviewCount());
    }
}