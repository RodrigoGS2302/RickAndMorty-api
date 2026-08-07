package com.br.rickandmortyapi.models.dto;

import java.util.List;

public record CharacterResponse(
        Long id,
        Long externalId,
        String name,
        String status,
        String species,
        String origin,
        Boolean active,
        List<EpisodeResponse>episodes
) {
}