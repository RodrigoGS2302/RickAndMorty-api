package com.br.rickandmortyapi.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "episode")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Episode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "external_id" )
    private Long externalId;

    private String name;

    @Column (name = "episode_code")
    private String episodeCode;

    @ManyToMany(mappedBy = "episodes")
    private Set<Character> characters = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Episode episode)) {
            return false;
        }

        return Objects.equals(id, episode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
