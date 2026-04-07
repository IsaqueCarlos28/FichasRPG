package com.senac.tsi.CallOfCthulhuRPG.controllers;

import com.senac.tsi.CallOfCthulhuRPG.repositories.HistoricoFichaRepositorio;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.tsi.CallOfCthulhuRPG.domains.historico.HistoricoFicha;
import com.senac.tsi.CallOfCthulhuRPG.exceptions.HistoricoNotFoundException;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/historicos")
public class HistoricoFichaController {

    private final HistoricoFichaRepositorio repository;
    private final PagedResourcesAssembler<HistoricoFicha> assembler;

    public HistoricoFichaController(HistoricoFichaRepositorio repository,
                                    PagedResourcesAssembler<HistoricoFicha> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<HistoricoFicha>>> getAll(@ParameterObject Pageable pageable) {
        var page = repository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(page));
    }

    @GetMapping("/{id}")
    public EntityModel<HistoricoFicha> getById(@PathVariable Long id) throws HistoricoNotFoundException {

        var entity = repository.findById(id)
                .orElseThrow(() -> new HistoricoNotFoundException("Historico " + id + "   não encontrado"));

        return EntityModel.of(entity,
                linkTo(methodOn(HistoricoFichaController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(HistoricoFichaController.class).getAll(Pageable.unpaged())).withRel("historicos"));
    }

    @PostMapping
    public ResponseEntity<HistoricoFicha> create(@RequestBody HistoricoFicha entity) {
        repository.save(entity);
        return ResponseEntity
                .created(URI.create("/historicos/" + entity.getId()))
                .body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoFicha> update(@PathVariable Long id,
                                                 @RequestBody HistoricoFicha updated) throws HistoricoNotFoundException {

        return repository.findById(id).map(entity -> {

            entity.setDescricaoPessoais(updated.getDescricaoPessoais());
            entity.setIdeologias(updated.getIdeologias());
            entity.setPessoasImportantes(updated.getPessoasImportantes());
            entity.setItensValiosos(updated.getItensValiosos());
            entity.setTracos(updated.getTracos());
            entity.setTomosArcanos(updated.getTomosArcanos());
            entity.setEncontrosEstranhos(updated.getEncontrosEstranhos());
            entity.setMachucadosECicatrizes(updated.getMachucadosECicatrizes());
            entity.setFobiasEManias(updated.getFobiasEManias());

            return ResponseEntity.ok(repository.save(entity));

        }).orElseThrow(() -> new HistoricoNotFoundException("Historico " + id + "   não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws HistoricoNotFoundException {

        if (!repository.existsById(id))
            throw new HistoricoNotFoundException("Historico " + id + "   não encontrado");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}