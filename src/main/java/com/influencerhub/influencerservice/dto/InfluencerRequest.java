package com.influencerhub.influencerservice.dto;

import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class InfluencerRequest {
    private String name;
    private Set<String> genres;
    private String bio;
    private String profileImageUrl;
    private Double hourlyRate;
    private List<String> servicesOffered;
}