package com.br.rickandmortyapi.sevice;

import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import com.br.rickandmortyapi.client.dto.OriginClientResponse;
import com.br.rickandmortyapi.exceptions.CharacterAlreadyExistsException;
import com.br.rickandmortyapi.exceptions.CharacterNotFoundException;
import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.models.dto.CharacterUpdateRequest;
import com.br.rickandmortyapi.models.entities.Character;
import com.br.rickandmortyapi.models.entities.Episode;
import com.br.rickandmortyapi.models.mapper.CharacterMapper;
import com.br.rickandmortyapi.models.mapper.EpisodeMapper;
import com.br.rickandmortyapi.repositories.CharacterRepository;
import com.br.rickandmortyapi.repositories.EpisodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @Mock
    private RickAndMortyIntegrationService rickAndMortyIntegrationService;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private CharacterMapper characterMapper;

    @Mock
    private EpisodeMapper episodeMapper;

    @InjectMocks
    private CharacterService characterService;

    private Character character;
    private CharacterResponse characterResponse;

    @BeforeEach
    void setUp() {

        character = new Character();
        character.setId(1L);
        character.setExternalId(16L);
        character.setName("Amish Cyborg");
        character.setStatus("Dead");
        character.setSpecies("Alien");
        character.setOrigin("unknown");
        character.setActive(true);

        characterResponse = new CharacterResponse(
                1L,
                16L,
                "Amish Cyborg",
                "Dead",
                "Alien",
                "unknown",
                true,
                List.of()
        );
    }

    @Test
    void shouldFindCharacterById() {

        when(characterRepository.findById(1L))
                .thenReturn(Optional.of(character));

        when(characterMapper.toCharacterResponse(character))
                .thenReturn(characterResponse);

        CharacterResponse result = characterService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(16L, result.externalId());
        assertEquals("Amish Cyborg", result.name());

        verify(characterRepository).findById(1L);
        verify(characterMapper).toCharacterResponse(character);
    }

    @Test
    void shouldThrowExceptionWhenCharacterByIdDoesNotExist() {

        when(characterRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                CharacterNotFoundException.class,
                () -> characterService.findById(99L)
        );

        verify(characterRepository).findById(99L);
        verifyNoInteractions(characterMapper);
    }

    @Test
    void shouldFindCharacterByName() {

        when(characterRepository
                .findByNameIgnoreCaseAndActiveTrue("Amish Cyborg"))
                .thenReturn(Optional.of(character));

        when(characterMapper.toCharacterResponse(character))
                .thenReturn(characterResponse);

        CharacterResponse result =
                characterService.findByName("Amish Cyborg");

        assertNotNull(result);
        assertEquals("Amish Cyborg", result.name());

        verify(characterRepository)
                .findByNameIgnoreCaseAndActiveTrue("Amish Cyborg");
    }

    @Test
    void shouldThrowExceptionWhenCharacterByNameDoesNotExist() {

        when(characterRepository
                .findByNameIgnoreCaseAndActiveTrue("Rick"))
                .thenReturn(Optional.empty());

        assertThrows(
                CharacterNotFoundException.class,
                () -> characterService.findByName("Rick")
        );
    }

    @Test
    void shouldUpdateCharacter() {

        CharacterUpdateRequest request =
                new CharacterUpdateRequest(
                        "Amish Cyborg Atualizado",
                        "Alive",
                        "Alien",
                        "Earth"
                );

        when(characterRepository.findById(1L))
                .thenReturn(Optional.of(character));

        when(characterRepository.save(character))
                .thenReturn(character);

        when(characterMapper.toCharacterResponse(character))
                .thenReturn(characterResponse);

        CharacterResponse result =
                characterService.updateCharacter(1L, request);

        assertNotNull(result);

        verify(characterRepository).findById(1L);

        verify(characterMapper)
                .updateEntityFromRequest(character, request);

        verify(characterRepository).save(character);

        verify(characterMapper)
                .toCharacterResponse(character);
    }

    @Test
    void shouldSoftDeleteCharacter() {

        CharacterResponse deletedResponse =
                new CharacterResponse(
                        1L,
                        16L,
                        "Amish Cyborg",
                        "Dead",
                        "Alien",
                        "unknown",
                        false,
                        List.of()
                );

        when(characterRepository.findById(1L))
                .thenReturn(Optional.of(character));

        when(characterRepository.save(character))
                .thenReturn(character);

        when(characterMapper.toCharacterResponse(character))
                .thenReturn(deletedResponse);

        CharacterResponse result =
                characterService.deleteCharacter(1L);

        assertFalse(character.getActive());
        assertFalse(result.active());

        verify(characterRepository).save(character);
    }

    @Test
    void shouldImportCharacter() {

        OriginClientResponse origin =
                new OriginClientResponse(
                        "unknown",
                        ""
                );

        CharacterClientResponse clientResponse =
                new CharacterClientResponse(
                        16L,
                        "Amish Cyborg",
                        "Dead",
                        "Alien",
                        origin,
                        List.of(
                                "https://rickandmortyapi.com/api/episode/15"
                        )
                );

        EpisodeClientResponse episodeClientResponse =
                new EpisodeClientResponse(
                        15L,
                        "Total Rickall",
                        "S02E04"
                );

        Episode episode = new Episode();
        episode.setId(1L);
        episode.setExternalId(15L);
        episode.setName("Total Rickall");
        episode.setEpisodeCode("S02E04");

        when(characterRepository.findByExternalId(16L))
                .thenReturn(Optional.empty());

        when(rickAndMortyIntegrationService.findCharacterById(16L))
                .thenReturn(clientResponse);

        when(characterMapper.toCharacter(clientResponse))
                .thenReturn(character);

        when(episodeRepository.findByExternalId(15L))
                .thenReturn(Optional.empty());

        when(rickAndMortyIntegrationService.findEpisodeById(15L))
                .thenReturn(episodeClientResponse);

        when(episodeMapper.toEpisode(episodeClientResponse))
                .thenReturn(episode);

        when(episodeRepository.save(episode))
                .thenReturn(episode);

        when(characterRepository.save(character))
                .thenReturn(character);

        when(characterMapper.toCharacterResponse(character))
                .thenReturn(characterResponse);

        CharacterResponse result =
                characterService.importCharacterById(16L);

        assertNotNull(result);

        assertTrue(
                character.getEpisodes().contains(episode)
        );

        verify(characterRepository)
                .findByExternalId(16L);

        verify(rickAndMortyIntegrationService)
                .findCharacterById(16L);

        verify(rickAndMortyIntegrationService)
                .findEpisodeById(15L);

        verify(episodeRepository)
                .save(episode);

        verify(characterRepository)
                .save(character);
    }

    @Test
    void shouldUseExistingEpisodeWhenImportingCharacter() {

        OriginClientResponse origin =
                new OriginClientResponse("unknown", "");

        CharacterClientResponse clientResponse =
                new CharacterClientResponse(
                        16L,
                        "Amish Cyborg",
                        "Dead",
                        "Alien",
                        origin,
                        List.of(
                                "https://rickandmortyapi.com/api/episode/15"
                        )
                );

        Episode existingEpisode = new Episode();
        existingEpisode.setId(1L);
        existingEpisode.setExternalId(15L);

        when(characterRepository.findByExternalId(16L))
                .thenReturn(Optional.empty());

        when(rickAndMortyIntegrationService.findCharacterById(16L))
                .thenReturn(clientResponse);

        when(characterMapper.toCharacter(clientResponse))
                .thenReturn(character);

        when(episodeRepository.findByExternalId(15L))
                .thenReturn(Optional.of(existingEpisode));

        when(characterRepository.save(character))
                .thenReturn(character);

        when(characterMapper.toCharacterResponse(character))
                .thenReturn(characterResponse);

        CharacterResponse result =
                characterService.importCharacterById(16L);

        assertNotNull(result);

        assertTrue(
                character.getEpisodes().contains(existingEpisode)
        );

        verify(
                rickAndMortyIntegrationService,
                never()
        ).findEpisodeById(15L);

        verify(
                episodeRepository,
                never()
        ).save(any(Episode.class));
    }

    @Test
    void shouldThrowExceptionWhenCharacterAlreadyExists() {

        when(characterRepository.findByExternalId(16L))
                .thenReturn(Optional.of(character));

        assertThrows(
                CharacterAlreadyExistsException.class,
                () -> characterService.importCharacterById(16L)
        );

        verify(characterRepository)
                .findByExternalId(16L);

        verifyNoInteractions(
                rickAndMortyIntegrationService
        );
    }
}