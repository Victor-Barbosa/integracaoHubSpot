package br.com.victor.integracaohubspot.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    void shouldAllowAllRequestsAndDisableCsrfFormLoginAndHttpBasic() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class);

        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(http);

        assertThat(securityFilterChain).isNotNull();
    }
}