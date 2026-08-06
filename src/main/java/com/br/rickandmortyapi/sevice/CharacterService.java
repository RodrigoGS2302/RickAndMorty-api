package com.br.rickandmortyapi.sevice;


import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.models.entities.Episode;
import com.br.rickandmortyapi.models.mapper.CharacterMapper;
import com.br.rickandmortyapi.models.mapper.EpisodeMapper;
import com.br.rickandmortyapi.repositories.CharacterRepository;
import com.br.rickandmortyapi.repositories.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.br.rickandmortyapi.models.entities.Character;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final RickAndMortyIntegrationService rickAndMortyIntegrationService;

    private final CharacterRepository characterRepository;

    private final EpisodeRepository episodeRepository;

    private final CharacterMapper characterMapper;

    private final EpisodeMapper episodeMapper;


    public CharacterResponse importCharacterById (Long id){

        characterRepository.findByExternalId(id).ifPresent( character -> {
            throw new RuntimeException("Character already registered");
        });

        CharacterClientResponse characterClientResponse = rickAndMortyIntegrationService.findCharacterById(id);

        Character character = characterMapper.toCharacter(characterClientResponse);

        for (String episodeUrl : characterClientResponse.episode()) {

            String episodeIdText = episodeUrl.substring(episodeUrl.lastIndexOf("/") + 1);

            Long episodeId = Long.valueOf(episodeIdText);

            EpisodeClientResponse episodeClientResponse = rickAndMortyIntegrationService.findEpisodeById(episodeId);

            Episode episode = episodeMapper.toEpisode(episodeClientResponse);

            Optional<Episode> optionalEpisode = episodeRepository.findByExternalId(episode.getExternalId());

            if (optionalEpisode.isPresent()) {
                episode = optionalEpisode.get();
            } else {
                episode = episodeRepository.save(episode);
            }

            character.getEpisodes().add(episode);
        }

        Character savedCharacter = characterRepository.save(character);

        return characterMapper.toCharacterResponse(savedCharacter);
    }

}
