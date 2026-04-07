package com.senac.tsi.CallOfCthulhuRPG.controllers;

import com.senac.tsi.CallOfCthulhuRPG.domains.atributos.AtributosFicha;
import com.senac.tsi.CallOfCthulhuRPG.exceptions.AtributosNotFoundException;
import com.senac.tsi.CallOfCthulhuRPG.repositories.AtributosFichaRepositorio;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/atributos")
public class AtributosFichaController {

    private final AtributosFichaRepositorio repository;
    private final PagedResourcesAssembler<AtributosFicha> assembler;

    public AtributosFichaController(AtributosFichaRepositorio repository,
                                    PagedResourcesAssembler<AtributosFicha> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AtributosFicha>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(assembler.toModel(repository.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public EntityModel<AtributosFicha> getById(@PathVariable Long id) throws AtributosNotFoundException {
        return EntityModel.of(
                repository.findById(id)
                        .orElseThrow(() -> new AtributosNotFoundException("Atributo " +  id + " nao encontrado"))
        );
    }

    @PostMapping
    public ResponseEntity<AtributosFicha> create(@RequestBody AtributosFicha entity) {
        repository.save(entity);
        return ResponseEntity.created(URI.create("/atributos/" + entity.getId())).body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtributosFicha> update(@PathVariable Long id,
                                                 @RequestBody AtributosFicha updated) throws AtributosNotFoundException {

        return repository.findById(id).map(entity -> {
            entity.setAtributosPrimarios(updated.getAtributosPrimarios());
            entity.setSorte(updated.getSorte());
            entity.calcularFicha();

            return ResponseEntity.ok(repository.save(entity));
        }).orElseThrow(() -> new AtributosNotFoundException("Atributo " +  id + " nao encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws AtributosNotFoundException {
        if (!repository.existsById(id))
            throw new AtributosNotFoundException("Atributo " +  id + " nao encontrado");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}