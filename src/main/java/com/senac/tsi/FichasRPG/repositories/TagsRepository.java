package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String nameTag);
    Boolean existsByName(String tagName);
}
