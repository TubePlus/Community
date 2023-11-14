package com.example.community_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.kafka.annotation.EnableKafka;

@EnableJpaAuditing // base entity 자동 적용
@SpringBootApplication
@EnableCaching
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
