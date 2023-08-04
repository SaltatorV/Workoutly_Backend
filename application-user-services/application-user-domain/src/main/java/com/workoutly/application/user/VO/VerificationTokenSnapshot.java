package com.workoutly.application.user.VO;

import java.util.Date;
import java.util.Objects;

public class VerificationTokenSnapshot {

    private final TokenId tokenId;
    private final String token;
    private final Date expireTime;

    public VerificationTokenSnapshot(TokenId tokenId, String token, Date expireTime) {
        this.tokenId = tokenId;
        this.token = token;
        this.expireTime = expireTime;
    }

    public TokenId getTokenId() {
        return tokenId;
    }

    public String getToken() {
        return token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationTokenSnapshot that = (VerificationTokenSnapshot) o;
        return Objects.equals(token, that.token) && Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, expireTime);
    }
}
