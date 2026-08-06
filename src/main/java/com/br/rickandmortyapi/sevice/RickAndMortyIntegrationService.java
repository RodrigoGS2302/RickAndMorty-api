package com.br.rickandmortyapi.sevice;

import com.br.rickandmortyapi.client.RickAndMortyClient;
import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RickAndMortyIntegrationService {

    private final RickAndMortyClient rickAndMortyClient;

    public CharacterClientResponse findCharacterById (Long id){

        return  rickAndMortyClient.findCharacterById(id);
    }

    public EpisodeClientResponse findEpisodeById (Long id){

        return rickAndMortyClient.findEpisodeById(id);
    }

}
