package com.br.rickandmortyapi.sevice;

import com.br.rickandmortyapi.client.RickAndMortyClient;
import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import com.br.rickandmortyapi.client.dto.OriginClientResponse;
import com.br.rickandmortyapi.exceptions.RickAndMortyIntegrationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RickAndMortyIntegrationServiceTest {

    @Mock
    private RickAndMortyClient rickAndMortyClient;

    @InjectMocks
    private RickAndMortyIntegrationService integrationService;

    @Test
    void shouldFindCharacterById() {

        CharacterClientResponse response =
                new CharacterClientResponse(
                        1L,
                        "Rick Sanchez",
                        "Alive",
                        "Human",
                        new OriginClientResponse(
                                "Earth",
                                "url"
                        ),
                        List.of()
                );

        when(rickAndMortyClient.findCharacterById(1L))
                .thenReturn(response);

        CharacterClientResponse result =
                integrationService.findCharacterById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Rick Sanchez", result.name());

        verify(rickAndMortyClient)
                .findCharacterById(1L);
    }

    @Test
    void shouldThrowIntegrationExceptionWhenCharacterApiFails() {

        when(rickAndMortyClient.findCharacterById(1L))
                .thenThrow(new RuntimeException());

        assertThrows(
                RickAndMortyIntegrationException.class,
                () -> integrationService.findCharacterById(1L)
        );
    }

    @Test
    void shouldFindEpisodeById() {

        EpisodeClientResponse response =
                new EpisodeClientResponse(
                        15L,
                        "Total Rickall",
                        "S02E04"
                );

        when(rickAndMortyClient.findEpisodeById(15L))
                .thenReturn(response);

        EpisodeClientResponse result =
                integrationService.findEpisodeById(15L);

        assertNotNull(result);
        assertEquals(15L, result.id());
        assertEquals("Total Rickall", result.name());

        verify(rickAndMortyClient)
                .findEpisodeById(15L);
    }

    @Test
    void shouldThrowIntegrationExceptionWhenEpisodeApiFails() {

        when(rickAndMortyClient.findEpisodeById(15L))
                .thenThrow(new RuntimeException());

        assertThrows(
                RickAndMortyIntegrationException.class,
                () -> integrationService.findEpisodeById(15L)
        );
    }
}