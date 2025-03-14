package br.com.victor.integracaohubspot.authorization.domain;

public interface OAuthProperties {

    String getAuthUrl();
    String getClientId();
    String getRedirectUri();
    String getScopes();
}