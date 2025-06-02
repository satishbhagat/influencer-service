package com.influencerhub.influencerservice.service;

import com.influencerhub.influencerservice.dto.InfluencerRequest;
import com.influencerhub.influencerservice.dto.InfluencerResponse;
import com.influencerhub.influencerservice.entity.Influencer;
import com.influencerhub.influencerservice.repository.InfluencerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfluencerService {

    @Autowired
    private InfluencerRepository influencerRepository;

    // Mapper methods (consider using MapStruct for more complex mapping)
    private InfluencerResponse mapToResponse(Influencer influencer) {
        return new InfluencerResponse(
                influencer.getId(),
                influencer.getName(),
                influencer.getGenres(),
                influencer.getBio(),
                influencer.getProfileImageUrl(),
                influencer.getHourlyRate(),
                influencer.getServicesOffered()
        );
    }

    private Influencer mapToEntity(InfluencerRequest request) {
        Influencer influencer = new Influencer();
        influencer.setName(request.getName());
        influencer.setGenres(request.getGenres());
        influencer.setBio(request.getBio());
        influencer.setProfileImageUrl(request.getProfileImageUrl());
        influencer.setHourlyRate(request.getHourlyRate());
        influencer.setServicesOffered(request.getServicesOffered());
        return influencer;
    }

    @Transactional
    public InfluencerResponse createInfluencer(InfluencerRequest influencerRequest) {
        Influencer influencer = mapToEntity(influencerRequest);
        Influencer savedInfluencer = influencerRepository.save(influencer);
        return mapToResponse(savedInfluencer);
    }

    @Transactional(readOnly = true)
    public List<InfluencerResponse> getAllInfluencers() {
        return influencerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InfluencerResponse getInfluencerById(Long id) {
        Influencer influencer = influencerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Influencer not found with id: " + id)); // Create custom exception
        return mapToResponse(influencer);
    }

    @Transactional
    public InfluencerResponse updateInfluencer(Long id, InfluencerRequest influencerRequest) {
        Influencer existingInfluencer = influencerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Influencer not found with id: " + id));

        existingInfluencer.setName(influencerRequest.getName());
        existingInfluencer.setGenres(influencerRequest.getGenres());
        existingInfluencer.setBio(influencerRequest.getBio());
        existingInfluencer.setProfileImageUrl(influencerRequest.getProfileImageUrl());
        existingInfluencer.setHourlyRate(influencerRequest.getHourlyRate());
        existingInfluencer.setServicesOffered(influencerRequest.getServicesOffered());

        Influencer updatedInfluencer = influencerRepository.save(existingInfluencer);
        return mapToResponse(updatedInfluencer);
    }

    @Transactional
    public void deleteInfluencer(Long id) {
        if (!influencerRepository.existsById(id)) {
            throw new RuntimeException("Influencer not found with id: " + id);
        }
        influencerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<InfluencerResponse> searchInfluencers(String name, String genre) {
        if (name != null && !name.isEmpty() && genre != null && !genre.isEmpty()) {
            // A more specific query would be better here, e.g., combining criteria
            return influencerRepository.findByNameContainingIgnoreCase(name).stream()
                    .filter(inf -> inf.getGenres().stream().anyMatch(g -> g.equalsIgnoreCase(genre)))
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } else if (name != null && !name.isEmpty()) {
            return influencerRepository.findByNameContainingIgnoreCase(name).stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } else if (genre != null && !genre.isEmpty()) {
            return influencerRepository.findByGenresContainingIgnoreCase(genre).stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }
        return getAllInfluencers();
    }
}