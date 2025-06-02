package com.influencerhub.influencerservice;

import com.influencerhub.influencerservice.entity.Influencer;
import com.influencerhub.influencerservice.repository.InfluencerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InfluencerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfluencerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(InfluencerRepository influencerRepository) {
		return args -> {
			if (influencerRepository.count() == 0) { // Seed only if DB is empty
				influencerRepository.saveAll(List.of(
						new Influencer(null, "Alex TechGuru", new HashSet<>(Arrays.asList("Technology", "Gadgets", "AI")), "Reviews latest tech.", "https://placehold.co/400x300/FFC107/000000?text=AT", 150.0, Arrays.asList("Product Reviews", "Unboxing")),
						new Influencer(null, "Fiona Foodie", new HashSet<>(Arrays.asList("Cooking", "Food Travel")), "Exploring culinary delights.", "https://placehold.co/400x300/4CAF50/FFFFFF?text=FF", 120.0, Arrays.asList("Recipe Creation", "Restaurant Reviews")),
						new Influencer(null, "Sam Fitness", new HashSet<>(Arrays.asList("Fitness", "Wellness")), "Your fitness journey partner.", "https://placehold.co/400x300/E91E63/FFFFFF?text=SF", 100.0, Arrays.asList("Workout Plans", "Nutrition Tips"))
				));
				System.out.println("Seeded initial influencers.");
			}
		};
	}
}