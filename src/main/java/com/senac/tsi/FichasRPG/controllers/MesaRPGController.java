package com.senac.tsi.FichasRPG.controllers;


import com.senac.tsi.FichasRPG.domains.mesa.MesaRPG;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.assemblers.MesaAssembler;

import com.senac.tsi.FichasRPG.repositories.MesasRPGRepository;
import com.senac.tsi.FichasRPG.repositories.TagRepository;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mesas")
public class MesaRPGController {

    private final MesasRPGRepository repository;
    private final TagRepository tagRepository;
    private final PagedResourcesAssembler<MesaRPG> pagedAssembler;
    private final MesaAssembler assembler;

    public MesaRPGController(MesasRPGRepository repository,
                             PagedResourcesAssembler<MesaRPG> pagedAssembler,
                             MesaAssembler assembler,
                             TagRepository tagsRepository) {
        this.repository = repository;
        this.pagedAssembler = pagedAssembler;
        this.assembler = assembler;
        this.tagRepository = tagsRepository;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<MesaRPG>>> getAll(@ParameterObject Pageable pageable) {
        var mesas = repository.findAll(pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(mesas, assembler));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<MesaRPG>> getById(@PathVariable Long id) {
        var mesa = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",id));

        return ResponseEntity.ok(assembler.toModel(mesa));
    }

    // 🔥 GET mesas by Tag
    @GetMapping("/tag/{tagId}")
    public ResponseEntity<PagedModel<EntityModel<MesaRPG>>> getByTagId(@PathVariable Long tagId,
                                                                  @ParameterObject Pageable pageable) {

        var mesas = repository.findByTags_Id(tagId, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(mesas, assembler));
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<PagedModel<EntityModel<MesaRPG>>> getByTagName(@PathVariable String tagName,
                                                                     @ParameterObject Pageable pageable) {

        var mesas = repository.findByTags_NomeTag(tagName, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(mesas, assembler));
    }

    @PostMapping
    public ResponseEntity<EntityModel<MesaRPG>> create(@RequestBody MesaRPG mesa) {
        repository.save(mesa);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(mesa));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<MesaRPG>> update(@PathVariable Long id,
                                                    @RequestBody MesaRPG updated) {

        return repository.findById(id).map(mesa -> {

            mesa.setNome(updated.getNome());

            repository.save(mesa);
            return ResponseEntity.ok(assembler.toModel(mesa));

        }).orElseThrow(() -> new RPGNotFoundException("Mesa","id",id));
    }

    @PutMapping("/{mesaId}/tags/{tagId}")
    public ResponseEntity<EntityModel<MesaRPG>> addTag(
            @PathVariable Long mesaId,
            @PathVariable Long tagId) {

        var mesa = repository.findById(mesaId)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",mesaId));

        var tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RPGNotFoundException("Tag","id",tagId));

        // ✅ Idempotent behavior
        if (!mesa.getTags().contains(tag)) {
            mesa.addTag(tag);
            repository.save(mesa);
        }

        return ResponseEntity.ok(assembler.toModel(mesa));
    }

    @DeleteMapping("/{mesaId}/tags/{tagId}")
    public ResponseEntity<Void> removeTag(
            @PathVariable Long mesaId,
            @PathVariable Long tagId) {

        var mesa = repository.findById(mesaId)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",mesaId));

        var tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RPGNotFoundException("Tag","id",tagId));

        // ✅ Idempotent: remove only if exists
        if (mesa.getTags().contains(tag)) {
            mesa.removeTag(tag);
            repository.save(mesa);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var mesa = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",id));

        repository.delete(mesa);
        return ResponseEntity.noContent().build();
    }
}