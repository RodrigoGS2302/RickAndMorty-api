package com.br.rickandmortyapi.client;

import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "rickAndMortyClient",
        url = "${rickandmorty.api.url}"
)
public interface RickAndMortyClient {

    @GetMapping("/character/{id}")
    CharacterClientResponse findCharacterById(@PathVariable Long id);

    @GetMapping("/episode/{id}")
    EpisodeClientResponse findEpisodeById(@PathVariable Long id);

}