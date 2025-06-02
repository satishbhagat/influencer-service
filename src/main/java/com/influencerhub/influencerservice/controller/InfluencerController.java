package com.influencerhub.influencerservice.controller;

import com.influencerhub.influencerservice.dto.InfluencerRequest;
import com.influencerhub.influencerservice.dto.InfluencerResponse;
import com.influencerhub.influencerservice.service.InfluencerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // For method-level security
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/influencers")
// @CrossOrigin(origins = "*") // Handled by global CORS config or Gateway typically
public class InfluencerController {

    @Autowired
    private InfluencerService influencerService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_admin') or hasRole('ADMIN')") // Example: Requires admin scope/role
    public ResponseEntity<InfluencerResponse> createInfluencer(@Valid @RequestBody InfluencerRequest influencerRequest) {
        InfluencerResponse createdInfluencer = influencerService.createInfluencer(influencerRequest);
        return new ResponseEntity<>(createdInfluencer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InfluencerResponse>> getAllInfluencers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre) {
        List<InfluencerResponse> influencers = influencerService.searchInfluencers(name, genre);
        return ResponseEntity.ok(influencers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfluencerResponse> getInfluencerById(@PathVariable Long id) {
        InfluencerResponse influencer = influencerService.getInfluencerById(id);
        return ResponseEntity.ok(influencer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin') or hasRole('ADMIN')") // Example
    public ResponseEntity<InfluencerResponse> updateInfluencer(@PathVariable Long id, @Valid @RequestBody InfluencerRequest influencerRequest) {
        InfluencerResponse updatedInfluencer = influencerService.updateInfluencer(id, influencerRequest);
        return ResponseEntity.ok(updatedInfluencer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_admin') or hasRole('ADMIN')") // Example
    public ResponseEntity<Void> deleteInfluencer(@PathVariable Long id) {
        influencerService.deleteInfluencer(id);
        return ResponseEntity.noContent().build();
    }
}