package com.br.rickandmortyapi.models.dto;

public record CharacterResponse(
        Long id,
        Long externalId,
        String name,
        String status,
        String species,
        String origin,
        Boolean active
) {
}