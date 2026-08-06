package com.br.rickandmortyapi.models.mapper;

import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.models.entities.Character;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {

    public Character toCharacter(CharacterClientResponse characterClientResponse){

        Character character = new Character();

        character.setId(characterClientResponse.id());
        character.setName(characterClientResponse.name());
        character.setStatus(characterClientResponse.status());
        character.setSpecies(characterClientResponse.species());
        character.setOrigin(characterClientResponse.origin().name());
        character.setActive(true);

        return character;
    }
}
