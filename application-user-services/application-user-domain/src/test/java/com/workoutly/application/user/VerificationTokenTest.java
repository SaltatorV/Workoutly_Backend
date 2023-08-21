package com.workoutly.application.user;

import com.workoutly.application.user.VO.VerificationTokenSnapshot;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationTokenTest {

    @Test
    public void testGenerateVerificationToken() {

        //when
        var token = VerificationToken.generateToken();

        //then
        assertTokenIsGenerated(token);
    }

    @Test
    public void testIsTokenNotExpired() {
        //given
        var token = VerificationToken.generateToken();

        //when
        var isTokenExpired = token.isTokenExpired(timeNow());

        //then
        assertFalse(isTokenExpired);
    }

    @Test
    public void testIsTokenExpired() {
        //given
        var token = VerificationToken.generateToken();

        //when
        var isTokenExpired = token.isTokenExpired(timeTwoHoursLater());

        //then
        assertTrue(isTokenExpired);
    }

    @Test
    public void testCreateVerificationTokenSnapshot() {
        //given
        var token = VerificationToken.generateToken();

        //when
        var snapshot = token.createTokenSnapshot();

        //then
        assertIsSnapshotValid(token, snapshot);
    }

    @Test
    public void testRestoreVerificationToken() {
        //given
        var token = VerificationToken.generateToken();
        var snapshot = token.createTokenSnapshot();

        //when
        var restored = VerificationToken.restore(snapshot);

        //then
        assertIsTokenValid(token, restored);
    }

    private Date timeNow() {
        return Date.from(Instant.now());
    }

    private Date timeTwoHoursLater() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(Instant.now()));
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        return calendar.getTime();
    }

    private void assertTokenIsGenerated(VerificationToken token) {
        assertNotNull(token.getId());
        assertNotNull(token.getTokenValue());
        assertNotNull(token.getExpireTime());
    }

    private void assertIsSnapshotValid(VerificationToken token, VerificationTokenSnapshot snapshot) {
        VerificationTokenSnapshot snapshotFromToken = new VerificationTokenSnapshot(token.getId(), token.getTokenValue(), token.getExpireTime());
        assertEquals(snapshotFromToken, snapshot);
    }

    private void assertIsTokenValid(VerificationToken expected, VerificationToken actual) {
        assertEquals(expected, actual);
    }
}
