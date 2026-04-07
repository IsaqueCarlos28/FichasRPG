package com.senac.tsi.CallOfCthulhuRPG.controllers;

import com.senac.tsi.CallOfCthulhuRPG.repositories.HabilidadeFichaRepositorio;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.tsi.CallOfCthulhuRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.CallOfCthulhuRPG.exceptions.HabilidadesNotFoundException;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/habilidades")
public class HabilidadesFichaController {

    private final HabilidadeFichaRepositorio repository;
    private final PagedResourcesAssembler<HabilidadesFicha> assembler;

    public HabilidadesFichaController(HabilidadeFichaRepositorio repository,
                                      PagedResourcesAssembler<HabilidadesFicha> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<HabilidadesFicha>>> getAll(@ParameterObject Pageable pageable) {
        var page = repository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(page));
    }

    @GetMapping("/{id}")
    public EntityModel<HabilidadesFicha> getById(@PathVariable Long id) throws HabilidadesNotFoundException {

        var entity = repository.findById(id)
                .orElseThrow(() -> new HabilidadesNotFoundException("Habilidade " + id + " não encontrada"));

        return EntityModel.of(entity,
                linkTo(methodOn(HabilidadesFichaController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(HabilidadesFichaController.class).getAll(Pageable.unpaged())).withRel("habilidades"));
    }

    @PostMapping
    public ResponseEntity<HabilidadesFicha> create(@RequestBody HabilidadesFicha entity) {
        repository.save(entity);
        return ResponseEntity
                .created(URI.create("/habilidades/" + entity.getId()))
                .body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabilidadesFicha> update(@PathVariable Long id,
                                                   @RequestBody HabilidadesFicha updated) throws HabilidadesNotFoundException {

        return repository.findById(id).map(entity -> {

            entity.setPericiasInvestigador(updated.getPericiasInvestigador());
            entity.setPericiasOcupacional(updated.getPericiasOcupacional());
            entity.setArmas(updated.getArmas());

            return ResponseEntity.ok(repository.save(entity));

        }).orElseThrow(() -> new HabilidadesNotFoundException("Habilidade " + id + " não encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws HabilidadesNotFoundException {

        if (!repository.existsById(id))
            throw new HabilidadesNotFoundException("Habilidade " + id + " não encontrada");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}