package com.br.rickandmortyapi.models.mapper;

import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.models.entities.Character;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {

    public Character toCharacter(CharacterClientResponse characterClientResponse) {

        Character character = new Character();

        character.setExternalId(characterClientResponse.id());
        character.setName(characterClientResponse.name());
        character.setStatus(characterClientResponse.status());
        character.setSpecies(characterClientResponse.species());
        character.setOrigin(characterClientResponse.origin().name());
        character.setActive(true);

        return character;
    }

    public CharacterResponse toCharacterResponse(Character character) {

        return new CharacterResponse(
                character.getId(),
                character.getExternalId(),
                character.getName(),
                character.getStatus(),
                character.getSpecies(),
                character.getOrigin(),
                character.getActive()
        );
    }
}
