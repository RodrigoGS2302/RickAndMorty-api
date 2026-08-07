package com.br.rickandmortyapi.models.dto;

public record EpisodeResponse(

        Long id,
        Long externalId,
        String name,
        String episodeCode
) {
}
