package com.br.rickandmortyapi.client.dto;

import java.util.List;

public record CharacterClientResponse(

        Long id,
        String name,
        String status,
        String species,
        OriginClientResponse origin,
        List<String> episode

) {
}
