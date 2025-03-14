package br.com.victor.integracaohubspot.authorization.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthTokenTest {

    @Test
    void shouldReturnFalseWhenTokenIsNotExpired() {
        String accessToken = "validAccessToken";
        String refreshToken = "validRefreshToken";
        int expiresIn = 1800;
        Instant generationTime = Instant.now().minusSeconds(1799);
        AuthToken authToken = new AuthToken(accessToken, refreshToken, expiresIn, generationTime);

        boolean expired = authToken.isExpired();

        assertFalse(expired, "Token should not be expired within the valid duration.");
    }

    @Test
    void shouldReturnTrueWhenTokenIsExpired() {
        String accessToken = "expiredAccessToken";
        String refreshToken = "expiredRefreshToken";
        int expiresIn = 1800;
        Instant generationTime = Instant.now().minusSeconds(3600);
        AuthToken authToken = new AuthToken(accessToken, refreshToken, expiresIn, generationTime);

        boolean expired = authToken.isExpired();

        assertTrue(expired, "Token should be expired after the valid duration.");
    }

    @Test
    void shouldReturnFalseForFreshlyGeneratedTokenWithDefaultConstructor() {
        AuthToken authToken = new AuthToken("newAccessToken", "newRefreshToken", 1800);

        boolean expired = authToken.isExpired();

        assertFalse(expired, "Freshly generated token should not be expired.");
    }
}