package com.influencerhub.influencerservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfluencerResponse {
    private Long id;
    private String name;
    private Set<String> genres;
    private String bio;
    private String profileImageUrl;
    private Double hourlyRate;
    private List<String> servicesOffered;
}