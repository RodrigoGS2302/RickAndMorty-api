package com.br.rickandmortyapi.models.mapper;

import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.client.dto.OriginClientResponse;
import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.models.dto.CharacterUpdateRequest;
import com.br.rickandmortyapi.models.dto.EpisodeResponse;
import com.br.rickandmortyapi.models.entities.Character;
import com.br.rickandmortyapi.models.entities.Episode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterMapperTest {

    @Mock
    private EpisodeMapper episodeMapper;

    @InjectMocks
    private CharacterMapper characterMapper;

    @Test
    void shouldConvertClientResponseToCharacter() {

        OriginClientResponse origin =
                new OriginClientResponse(
                        "Earth",
                        "url"
                );

        CharacterClientResponse clientResponse =
                new CharacterClientResponse(
                        1L,
                        "Rick Sanchez",
                        "Alive",
                        "Human",
                        origin,
                        List.of()
                );

        Character character =
                characterMapper.toCharacter(clientResponse);

        assertNotNull(character);

        assertNull(character.getId());

        assertEquals(
                1L,
                character.getExternalId()
        );

        assertEquals(
                "Rick Sanchez",
                character.getName()
        );

        assertEquals(
                "Alive",
                character.getStatus()
        );

        assertEquals(
                "Human",
                character.getSpecies()
        );

        assertEquals(
                "Earth",
                character.getOrigin()
        );

        assertTrue(character.getActive());
    }

    @Test
    void shouldConvertCharacterToCharacterResponse() {

        Character character = new Character();

        character.setId(1L);
        character.setExternalId(16L);
        character.setName("Amish Cyborg");
        character.setStatus("Dead");
        character.setSpecies("Alien");
        character.setOrigin("Earth");
        character.setActive(true);

        Episode episode = new Episode();
        episode.setId(10L);
        episode.setExternalId(15L);

        character.getEpisodes().add(episode);

        EpisodeResponse episodeResponse =
                new EpisodeResponse(
                        10L,
                        15L,
                        "Total Rickall",
                        "S02E04"
                );

        when(episodeMapper.toEpisodeResponse(episode))
                .thenReturn(episodeResponse);

        CharacterResponse response =
                characterMapper.toCharacterResponse(character);

        assertNotNull(response);

        assertEquals(1L, response.id());
        assertEquals(16L, response.externalId());
        assertEquals("Amish Cyborg", response.name());

        assertEquals(
                1,
                response.episodes().size()
        );

        assertEquals(
                "Total Rickall",
                response.episodes().get(0).name()
        );
    }

    @Test
    void shouldUpdateCharacterFromRequest() {

        Character character = new Character();

        character.setName("Nome antigo");
        character.setStatus("Dead");
        character.setSpecies("Alien");
        character.setOrigin("unknown");

        CharacterUpdateRequest request =
                new CharacterUpdateRequest(
                        "Novo nome",
                        "Alive",
                        "Human",
                        "Earth"
                );

        characterMapper.updateEntityFromRequest(
                character,
                request
        );

        assertEquals(
                "Novo nome",
                character.getName()
        );

        assertEquals(
                "Alive",
                character.getStatus()
        );

        assertEquals(
                "Human",
                character.getSpecies()
        );

        assertEquals(
                "Earth",
                character.getOrigin()
        );
    }
}