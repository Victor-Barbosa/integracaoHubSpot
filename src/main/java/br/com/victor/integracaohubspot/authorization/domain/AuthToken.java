package br.com.victor.integracaohubspot.authorization.domain;

import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class AuthToken {

    private final String accessToken;
    private final String refreshToken;
    private final int expiresIn;
    private final Instant generationTime;

    public AuthToken(String accessToken, String refreshToken, int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.generationTime = Instant.now();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(generationTime.plusSeconds(expiresIn));
    }
}