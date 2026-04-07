package com.senac.tsi.CallOfCthulhuRPG.controllers;

import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import com.senac.tsi.CallOfCthulhuRPG.exceptions.FichaNotFoundException;
import com.senac.tsi.CallOfCthulhuRPG.repositories.FichaRepositorio;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/fichas")
public class FichaController {

    private final FichaRepositorio repository;
    private final PagedResourcesAssembler<Ficha> assembler;

    public FichaController(FichaRepositorio repository,
                           PagedResourcesAssembler<Ficha> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Ficha>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(assembler.toModel(repository.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public EntityModel<Ficha> getById(@PathVariable Long id) throws FichaNotFoundException {
        var ficha = repository.findById(id)
                .orElseThrow(() -> new FichaNotFoundException("Ficha" + id + "não encontrada"));

        return EntityModel.of(ficha,
                linkTo(methodOn(FichaController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(FichaController.class).getAll(Pageable.unpaged())).withRel("fichas"));
    }

    @PostMapping
    public ResponseEntity<Ficha> create(@RequestBody Ficha ficha) {
        repository.save(ficha);
        return ResponseEntity.created(URI.create("/fichas/" + ficha.getId())).body(ficha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ficha> update(@PathVariable Long id, @RequestBody Ficha updated) throws FichaNotFoundException {

        return repository.findById(id).map(ficha -> {
            ficha.setNomeJogador(updated.getNomeJogador());
            ficha.setInvestigador(updated.getInvestigador());
            ficha.setAtributos(updated.getAtributos());
            ficha.setStatus(updated.getStatus());
            ficha.setHabilidades(updated.getHabilidades());
            ficha.setHistorico(updated.getHistorico());
            ficha.setItensEDinheiro(updated.getItensEDinheiro());
            ficha.setCompanheiros(updated.getCompanheiros());

            return ResponseEntity.ok(repository.save(ficha));
        }).orElseThrow(() -> new FichaNotFoundException("Ficha" + id + "não encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws FichaNotFoundException {
        if (!repository.existsById(id))
            throw new FichaNotFoundException("Ficha" + id + "não encontrada");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}