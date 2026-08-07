package com.br.rickandmortyapi.models.dto;

public record ErrorResponse(
        Integer status,
        String error,
        String message
) {
}