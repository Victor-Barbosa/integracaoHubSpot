package br.com.victor.integracaohubspot.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BucketConfigTest {

    @Test
    void testRequestBucket() {
        BucketConfig bucketConfig = new BucketConfig();

        Bucket requestBucket = bucketConfig.requestBucket();

        assertThat(requestBucket).isNotNull();
        Bandwidth expectedBandwidth = Bandwidth.classic(110, Refill.greedy(110,
                Duration.ofSeconds(10)));
        assertThat(requestBucket.getAvailableTokens()).isEqualTo(expectedBandwidth.getCapacity());
    }
}