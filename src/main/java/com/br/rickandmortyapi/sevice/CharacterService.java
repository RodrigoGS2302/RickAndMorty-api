package com.br.rickandmortyapi.sevice;


import com.br.rickandmortyapi.client.dto.CharacterClientResponse;
import com.br.rickandmortyapi.client.dto.EpisodeClientResponse;
import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.models.dto.CharacterUpdateRequest;
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



    public CharacterResponse importCharacterById(Long id) {

        validateCharacterAlreadyExists(id);

        CharacterClientResponse characterClientResponse = rickAndMortyIntegrationService.findCharacterById(id);

        Character character = characterMapper.toCharacter(characterClientResponse);

        addEpisodes(character, characterClientResponse);

        Character savedCharacter = characterRepository.save(character);

        return characterMapper.toCharacterResponse(savedCharacter);
    }

    public CharacterResponse updateCharacter (Long id,  CharacterUpdateRequest characterUpdateRequest){

        Character character = findCharacterById(id);

        characterMapper.updateEntityFromRequest(character, characterUpdateRequest);

        Character savedCharacter = characterRepository.save(character);

        return characterMapper.toCharacterResponse(savedCharacter);

    }

    public void deleteCharacter(Long id){

        Character character = findCharacterById(id);

        character.setActive(false);

        characterRepository.save(character);

    }

    private void validateCharacterAlreadyExists(Long id) {

        characterRepository.findByExternalId(id).ifPresent(character -> {
            throw new RuntimeException("Character already registered");
        });
    }

    private void addEpisodes(Character character, CharacterClientResponse characterClientResponse) {

        for (String episodeUrl : characterClientResponse.episode()) {

            Long episodeId = extractEpisodeId(episodeUrl);

            Episode episode = findOrCreateEpisode(episodeId);

            character.getEpisodes().add(episode);
        }
    }

    private Long extractEpisodeId(String episodeUrl) {

        String episodeIdText = episodeUrl.substring(episodeUrl.lastIndexOf("/") + 1);

        return Long.valueOf(episodeIdText);
    }

    private Episode findOrCreateEpisode(Long episodeId) {

        Optional<Episode> optionalEpisode = episodeRepository.findByExternalId(episodeId);

        if (optionalEpisode.isPresent()) {
            return optionalEpisode.get();
        }

        EpisodeClientResponse episodeClientResponse = rickAndMortyIntegrationService.findEpisodeById(episodeId);

        Episode episode = episodeMapper.toEpisode(episodeClientResponse);

        return episodeRepository.save(episode);
    }

    private Character findCharacterById (Long id){

        return characterRepository.findById(id).orElseThrow(() -> new RuntimeException("Character not found"));
    }

}
