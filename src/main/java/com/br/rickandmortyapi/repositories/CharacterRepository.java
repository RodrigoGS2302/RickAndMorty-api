package com.br.rickandmortyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    Optional<Character> findByExternalId(Long externalId);

    Optional<Character> findByNameIgnoreCaseAndActiveTrue(String name);
}
