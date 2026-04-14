package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByNome(String nomeTag);
    boolean existsByNome(String nomeTag);
    boolean existsByNomeAndIdNot(String nomeTag, Long id);
}
