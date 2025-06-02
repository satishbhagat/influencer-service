package com.influencerhub.influencerservice.repository;

import com.influencerhub.influencerservice.entity.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    // Custom query methods if needed, e.g., find by genre
    List<Influencer> findByGenresContainingIgnoreCase(String genre);
    List<Influencer> findByNameContainingIgnoreCase(String name);
}