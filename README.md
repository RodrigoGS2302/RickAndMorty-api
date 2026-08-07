# Rick and Morty API

API REST desenvolvida em Java e Spring Boot para consulta, importação e gerenciamento de personagens da API pública Rick and Morty.

O projeto consome dados de uma API externa, armazena personagens e seus episódios em um banco PostgreSQL e disponibiliza endpoints para consulta, atualização e soft delete.

## Funcionalidades

- Importação de personagens da Rick and Morty API por ID
- Armazenamento dos personagens no banco de dados
- Importação e associação dos episódios do personagem
- Reutilização de episódios já cadastrados no banco
- Consulta de personagem por ID
- Consulta de personagem por nome
- Atualização dos dados do personagem
- Soft delete através do campo `active`
- Tratamento global de exceções
- Documentação dos endpoints com Swagger/OpenAPI
- Testes unitários

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- OpenFeign
- PostgreSQL
- Docker
- Maven
- Lombok
- Swagger / OpenAPI
- JUnit 5
- Mockito

## Estrutura

O projeto segue uma separação de responsabilidades em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Banco de dados
```

Para integração com a API externa:

```text
CharacterService
    ↓
RickAndMortyIntegrationService
    ↓
Feign Client
    ↓
Rick and Morty API
```

Também são utilizados DTOs e Mappers para separar as entidades persistidas dos objetos utilizados na comunicação da API.

## Endpoints

### Importar personagem

```http
POST /character/import/{id}
```

Consulta um personagem na Rick and Morty API e registra o personagem e seus episódios no banco de dados.

### Buscar personagem por ID

```http
GET /character/{id}
```

Retorna um personagem cadastrado através do ID interno.

### Buscar personagem por nome

```http
GET /character/name/{name}
```

Retorna um personagem ativo através do nome.

### Atualizar personagem

```http
PUT /character/{id}
```

Atualiza os dados de um personagem cadastrado.

Exemplo de body:

```json
{
  "name": "Amish Cyborg Atualizado",
  "status": "Alive",
  "species": "Alien",
  "origin": "Earth"
}
```

### Soft delete

```http
DELETE /character/{id}
```

Desativa o personagem alterando o campo `active` para `false`, sem remover o registro fisicamente do banco de dados.

## Exemplo de resposta

```json
{
  "id": 2,
  "externalId": 16,
  "name": "Amish Cyborg",
  "status": "Dead",
  "species": "Alien",
  "origin": "unknown",
  "active": true,
  "episodes": [
    {
      "id": 1,
      "externalId": 15,
      "name": "Total Rickall",
      "episodeCode": "S02E04"
    }
  ]
}
```

## Swagger

A documentação dos endpoints está disponível através do Swagger UI.

Com a aplicação em execução, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

A especificação OpenAPI também está disponível em:

```text
http://localhost:8080/v3/api-docs
```

## Banco de dados

O projeto utiliza PostgreSQL para persistência dos dados.

O banco pode ser executado utilizando Docker através do arquivo:

```text
docker-compose.yml
```

Para iniciar o container:

```bash
docker compose up -d
```

Para encerrar:

```bash
docker compose down
```

## Executando o projeto

Clone o repositório:

```bash
git clone URL_DO_SEU_REPOSITORIO
```

Entre na pasta:

```bash
cd rickandmortyapi
```

Inicie o banco de dados:

```bash
docker compose up -d
```

Execute a aplicação pela IDE ou utilizando Maven:

```bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

## Testes

O projeto possui testes unitários utilizando JUnit 5 e Mockito.

Os testes cobrem as principais regras da aplicação, incluindo:

- importação de personagens;
- reutilização de episódios existentes;
- atualização de personagens;
- consulta por ID;
- consulta por nome;
- soft delete;
- conversão entre entidades e DTOs;
- integração com o client externo;
- cenários de exceção.

Para executar os testes:

```bash
./mvnw test
```

## Tratamento de erros

A aplicação possui tratamento global de exceções para cenários como:

- personagem não encontrado;
- personagem já cadastrado;
- falha de comunicação com a API externa.

Os erros são retornados através de respostas HTTP padronizadas.

## API externa

Os dados utilizados para importação dos personagens são obtidos através da Rick and Morty API.

## Autor

Rodrigo Gomes