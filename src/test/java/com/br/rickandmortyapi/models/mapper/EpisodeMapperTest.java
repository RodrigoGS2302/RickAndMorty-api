package com.br.rickandmortyapi.models.mapper;

import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import com.br.rickandmortyapi.models.dto.EpisodeResponse;
import com.br.rickandmortyapi.models.entities.Episode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpisodeMapperTest {

    private final EpisodeMapper episodeMapper =
            new EpisodeMapper();

    @Test
    void shouldConvertClientResponseToEpisode() {

        EpisodeClientResponse clientResponse =
                new EpisodeClientResponse(
                        15L,
                        "Total Rickall",
                        "S02E04"
                );

        Episode episode =
                episodeMapper.toEpisode(clientResponse);

        assertNotNull(episode);

        assertEquals(
                15L,
                episode.getExternalId()
        );

        assertEquals(
                "Total Rickall",
                episode.getName()
        );

        assertEquals(
                "S02E04",
                episode.getEpisodeCode()
        );

        assertNull(episode.getId());
    }

    @Test
    void shouldConvertEpisodeToEpisodeResponse() {

        Episode episode = new Episode();

        episode.setId(1L);
        episode.setExternalId(15L);
        episode.setName("Total Rickall");
        episode.setEpisodeCode("S02E04");

        EpisodeResponse response =
                episodeMapper.toEpisodeResponse(episode);

        assertNotNull(response);

        assertEquals(1L, response.id());
        assertEquals(15L, response.externalId());
        assertEquals("Total Rickall", response.name());
        assertEquals("S02E04", response.episodeCode());
    }
}