package com.br.rickandmortyapi.exceptions;

public class CharacterAlreadyExistsException extends RuntimeException {

    public CharacterAlreadyExistsException(String message) {
        super(message);
    }
}
