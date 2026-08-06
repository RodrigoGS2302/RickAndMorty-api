package com.br.rickandmortyapi.controller;

import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.sevice.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping("/import/{id}")
    public ResponseEntity<CharacterResponse> importCharacter (@PathVariable Long id){

        CharacterResponse characterResponse  = characterService.importCharacterById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(characterResponse);

    }

}
