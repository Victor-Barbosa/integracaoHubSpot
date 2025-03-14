package br.com.victor.integracaohubspot.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BucketConfig {

    @Bean
    public Bucket requestBucket() {
        Bandwidth limit = Bandwidth.classic(110,
                Refill.greedy(110, Duration.ofSeconds(10)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}