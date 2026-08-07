package com.br.rickandmortyapi.sevice;

import com.br.rickandmortyapi.client.RickAndMortyClient;
import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import com.br.rickandmortyapi.exceptions.RickAndMortyIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RickAndMortyIntegrationService {

    private final RickAndMortyClient rickAndMortyClient;

    public CharacterClientResponse findCharacterById(Long id) {

        try {
            return rickAndMortyClient.findCharacterById(id);
        } catch (Exception exception) {
            throw new RickAndMortyIntegrationException(
                    "Error communicating with Rick and Morty API"
            );
        }
    }

    public EpisodeClientResponse findEpisodeById(Long id) {

        try {
            return rickAndMortyClient.findEpisodeById(id);
        } catch (Exception exception) {
            throw new RickAndMortyIntegrationException(
                    "Error communicating with Rick and Morty API"
            );
        }
    }

}
