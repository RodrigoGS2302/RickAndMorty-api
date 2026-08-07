package com.br.rickandmortyapi.controller;

import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.models.dto.CharacterUpdateRequest;
import com.br.rickandmortyapi.sevice.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<CharacterResponse> updateCharacter(@PathVariable Long id,
                                                             @RequestBody CharacterUpdateRequest characterUpdateRequest){

        CharacterResponse characterResponse = characterService.updateCharacter(id, characterUpdateRequest);

        return ResponseEntity.ok(characterResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Character> deleteCharacter(@PathVariable Long id){

        characterService.deleteCharacter(id);

        return ResponseEntity.noContent().build();

    }
    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> findBtId (@PathVariable Long id){

        CharacterResponse characterResponse = characterService.findById(id);

        return ResponseEntity.ok().body(characterResponse);
    }

    @GetMapping("/name/{name}")
    public  ResponseEntity<CharacterResponse> findByuName (@PathVariable String name){

        CharacterResponse characterResponse = characterService.findByName(name);

        return ResponseEntity.ok().body(characterResponse);

    }


}
