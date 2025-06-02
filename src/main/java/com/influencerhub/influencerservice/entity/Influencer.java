package com.influencerhub.influencerservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List; // Using List for genres for simplicity
import java.util.Set;


@Entity
@Table(name = "influencers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER) // Store genres as a collection of basic types
    @CollectionTable(name = "influencer_genres", joinColumns = @JoinColumn(name = "influencer_id"))
    @Column(name = "genre")
    private Set<String> genres;

    @Column(length = 1000)
    private String bio;

    private String profileImageUrl;
    private Double hourlyRate; // Example field

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "influencer_services_offered", joinColumns = @JoinColumn(name = "influencer_id"))
    @Column(name = "service_offered")
    private List<String> servicesOffered; // e.g., "Sponsored Post", "Video Shoutout"
}