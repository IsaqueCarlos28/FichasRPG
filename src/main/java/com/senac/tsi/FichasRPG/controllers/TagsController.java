package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.assemblers.TagAssembler;
import com.senac.tsi.FichasRPG.domains.tags.Tag;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.exceptions.TagNotFoundException;
import com.senac.tsi.FichasRPG.repositories.TagsRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags")
@RequestMapping("/tags")
public class TagsController {

    private final TagsRepository repository;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;
    private final TagAssembler tagAssembler;

    TagsController(TagsRepository repository, PagedResourcesAssembler<Tag> pagedResourcesAssembler, TagAssembler tagAssembler) {
        this.repository = repository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.tagAssembler = tagAssembler;

    }

    @Operation(summary = "Listar todas as Tags",
            description = "Retorna uma lista com todas as Tags existentes no sistema")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Tag>>> getAll(@ParameterObject Pageable pageable) {
        var tags = repository.findAll(pageable);

        PagedModel<EntityModel<Tag>> pagedModelTag = pagedResourcesAssembler.toModel(tags, tagAssembler);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pagedModelTag);
    }

    @GetMapping("/{id}")
    public EntityModel<Tag> getTagById(@PathVariable(name = "id") long id){
        var tag = repository.findById(id).orElseThrow(
                ()-> new RPGNotFoundException("Tag","id",id)
        );
        return tagAssembler.toModel(tag);
    }
}



