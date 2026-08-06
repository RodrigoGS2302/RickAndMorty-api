package com.br.rickandmortyapi.repositories;

import com.br.rickandmortyapi.models.entities.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    Optional<Episode> findByExternalId(Long externalId);
}
