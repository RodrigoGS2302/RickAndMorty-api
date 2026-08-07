package com.br.rickandmortyapi.models.mapper;

import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import com.br.rickandmortyapi.models.dto.EpisodeResponse;
import com.br.rickandmortyapi.models.entities.Episode;
import org.springframework.stereotype.Component;

@Component
public class EpisodeMapper {

    public Episode toEpisode (EpisodeClientResponse episodeClientResponse){

        Episode episode = new Episode();

        episode.setExternalId(episodeClientResponse.id());
        episode.setName(episodeClientResponse.name());
        episode.setEpisodeCode(episodeClientResponse.episode());

        return episode;
    }

    public EpisodeResponse toEpisodeResponse(Episode episode) {

        return new EpisodeResponse(
                episode.getId(),
                episode.getExternalId(),
                episode.getName(),
                episode.getEpisodeCode()
        );
    }

}
