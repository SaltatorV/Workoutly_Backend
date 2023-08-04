package com.workoutly.application.user;

import com.workoutly.application.user.VO.TokenId;
import com.workoutly.application.user.VO.VerificationTokenSnapshot;
import com.workoutly.common.entity.AggregateRoot;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class VerificationToken extends AggregateRoot<TokenId> {
    private static final int EXPIRE_TIME_IN_MIN = 60;

    private String token;
    private Date expireTime;

    public VerificationToken() {
    }

    private VerificationToken(TokenId tokenId, Date expireTime, String token) {
        setId(tokenId);
        this.expireTime = expireTime;
        this.token = token;
    }

    public static VerificationToken restore(VerificationTokenSnapshot snapshot) {
        return new VerificationToken(snapshot.getTokenId(), snapshot.getExpireTime(), snapshot.getToken());
    }

    public static VerificationToken generateToken() {
        return new VerificationToken(new TokenId(UUID.randomUUID()), calculateExpireDate(), createTokenValue());
    }

    public VerificationTokenSnapshot createTokenSnapshot() {
        return new VerificationTokenSnapshot(getId(), token, expireTime);
    }

    public boolean isTokenExpired(Date date) {
        return expireTime.before(date);
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public String getTokenValue() {
        return token;
    }

    private static String createTokenValue() {
        return UUID.randomUUID().toString();
    }

    private static Date calculateExpireDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, EXPIRE_TIME_IN_MIN);
        return new Date(calendar.getTime().getTime());
    }
}
