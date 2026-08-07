package com.br.rickandmortyapi.models.mapper;

import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.models.dto.CharacterUpdateRequest;
import com.br.rickandmortyapi.models.dto.EpisodeResponse;
import com.br.rickandmortyapi.models.entities.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterMapper {

    private final EpisodeMapper episodeMapper;

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

        List<EpisodeResponse> episodes = character.getEpisodes()
                .stream()
                .map(episodeMapper::toEpisodeResponse)
                .toList();

        return new CharacterResponse(
                character.getId(),
                character.getExternalId(),
                character.getName(),
                character.getStatus(),
                character.getSpecies(),
                character.getOrigin(),
                character.getActive(),
                episodes

        );
    }

    public void updateEntityFromRequest(Character character, CharacterUpdateRequest characterUpdateRequest){

        character.setName(characterUpdateRequest.name());
        character.setStatus(characterUpdateRequest.status());
        character.setSpecies(characterUpdateRequest.species());
        character.setOrigin(characterUpdateRequest.origin());
    }
}
