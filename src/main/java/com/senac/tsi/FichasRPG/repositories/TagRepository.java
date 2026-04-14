package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String nameTag);
    boolean existsByName(String tagName);
    boolean existsByNameAndIdNot(String tagName,Long id);
}
