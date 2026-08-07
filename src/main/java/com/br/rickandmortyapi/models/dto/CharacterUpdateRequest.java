package com.br.rickandmortyapi.models.dto;

public record CharacterUpdateRequest(

        String name,
        String status,
        String species,
        String origin

) {
}
