package br.com.victor.integracaohubspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IntegracaoHubSpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegracaoHubSpotApplication.class, args);
	}

}
