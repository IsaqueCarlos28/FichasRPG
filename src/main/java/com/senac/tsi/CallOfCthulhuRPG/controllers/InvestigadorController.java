package com.senac.tsi.CallOfCthulhuRPG.controllers;

import com.senac.tsi.CallOfCthulhuRPG.domains.personagens.Investigador;
import com.senac.tsi.CallOfCthulhuRPG.exceptions.InvestigadorNotFoundException;
import com.senac.tsi.CallOfCthulhuRPG.repositories.InvestigadorFichaRepositorio;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/investigadores")
public class InvestigadorController {

    private final InvestigadorFichaRepositorio repository;
    private final PagedResourcesAssembler<Investigador> assembler;

    public InvestigadorController(InvestigadorFichaRepositorio repository,
                                  PagedResourcesAssembler<Investigador> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Investigador>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(assembler.toModel(repository.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public EntityModel<Investigador> getById(@PathVariable Long id) throws InvestigadorNotFoundException {
        var entity = repository.findById(id)
                .orElseThrow(() -> new InvestigadorNotFoundException("Investigador " + id + "  não encontrada"));

        return EntityModel.of(entity);
    }

    @PostMapping
    public ResponseEntity<Investigador> create(@RequestBody Investigador entity) {
        repository.save(entity);
        return ResponseEntity.created(URI.create("/investigadores/" + entity.getId())).body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investigador> update(@PathVariable Long id,
                                               @RequestBody Investigador updated) throws InvestigadorNotFoundException {

        return repository.findById(id).map(entity -> {
            entity.setNomeInvestigador(updated.getNomeInvestigador());
            entity.setOcupacao(updated.getOcupacao());
            entity.setResidencia(updated.getResidencia());
            entity.setLocalNascimento(updated.getLocalNascimento());
            entity.setGenero(updated.getGenero());
            entity.setIdade(updated.getIdade());

            return ResponseEntity.ok(repository.save(entity));
        }).orElseThrow(() -> new InvestigadorNotFoundException("Investigador: " + id + " não encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws InvestigadorNotFoundException {
        if (!repository.existsById(id))
            throw new InvestigadorNotFoundException("Investigador " + id + "  não encontrada");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}