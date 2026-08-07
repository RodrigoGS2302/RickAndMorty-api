package com.br.rickandmortyapi.controller;


import com.br.rickandmortyapi.models.dto.CharacterResponse;
import com.br.rickandmortyapi.models.dto.CharacterUpdateRequest;
import com.br.rickandmortyapi.sevice.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Character",
        description = "Endpoints para gerenciamento de personagens"
)
@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @Operation(
            summary = "Importar personagem",
            description = "Consulta um personagem na API Rick and Morty pelo ID e salva seus dados no banco"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Personagem importado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Personagem já cadastrado"),
            @ApiResponse(responseCode = "502", description = "Erro ao consultar a API Rick and Morty")
    })
    @PostMapping("/import/{id}")
    public ResponseEntity<CharacterResponse> importCharacter(@PathVariable Long id) {

        CharacterResponse characterResponse =
                characterService.importCharacterById(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(characterResponse);
    }


    @Operation(
            summary = "Atualizar personagem",
            description = "Atualiza os dados de um personagem salvo no banco"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personagem atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Personagem não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CharacterResponse> updateCharacter(
            @PathVariable Long id,
            @RequestBody CharacterUpdateRequest characterUpdateRequest) {

        CharacterResponse characterResponse =
                characterService.updateCharacter(id, characterUpdateRequest);

        return ResponseEntity.ok(characterResponse);
    }


    @Operation(
            summary = "Realizar soft delete",
            description = "Desativa um personagem alterando o campo active para false sem remover o registro do banco"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personagem desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Personagem não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CharacterResponse> deleteCharacter(@PathVariable Long id) {

        CharacterResponse characterResponse = characterService.deleteCharacter(id);

        return ResponseEntity.ok(characterResponse);
    }


    @Operation(
            summary = "Buscar personagem por ID",
            description = "Consulta um personagem salvo no banco pelo ID interno"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personagem encontrado"),
            @ApiResponse(responseCode = "404", description = "Personagem não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> findById(@PathVariable Long id) {

        CharacterResponse characterResponse = characterService.findById(id);

        return ResponseEntity.ok(characterResponse);
    }


    @Operation(
            summary = "Buscar personagem por nome",
            description = "Consulta um personagem ativo pelo nome, ignorando diferenças entre letras maiúsculas e minúsculas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Personagem encontrado"),
            @ApiResponse(responseCode = "404", description = "Personagem não encontrado")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<CharacterResponse> findByName(@PathVariable String name) {

        CharacterResponse characterResponse = characterService.findByName(name);

        return ResponseEntity.ok(characterResponse);
    }
}