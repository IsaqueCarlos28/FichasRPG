package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.assemblers.ModeloFichaAssembler;
import com.senac.tsi.FichasRPG.domains.modeloFicha.ModeloFicha;
import com.senac.tsi.FichasRPG.exceptions.RPGAlreadyExistsException;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.ModeloFichaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modelos-ficha")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Modelo Ficha")
public class ModeloFichaController {

    private final ModeloFichaRepository repository;
    private final PagedResourcesAssembler<ModeloFicha> pagedAssembler;
    private final ModeloFichaAssembler assembler;

    public ModeloFichaController(ModeloFichaRepository repository,
                                 PagedResourcesAssembler<ModeloFicha> pagedAssembler,
                                 ModeloFichaAssembler assembler) {
        this.repository = repository;
        this.pagedAssembler = pagedAssembler;
        this.assembler = assembler;
    }

    // ✅ GET ALL
    @Operation(summary = "Listar todos os Modelos de Ficha")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ModeloFicha>>> getAll(
            @ParameterObject Pageable pageable) {

        var modelos = repository.findAll(pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(modelos, assembler)
        );
    }

    // ✅ GET BY ID
    @Operation(summary = "Buscar Modelo de Ficha por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modelo encontrado",
                    content = @Content(schema = @Schema(implementation = ModeloFicha.class))),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloFicha>> getById(@PathVariable Long id) {

        var modelo = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("ModeloFicha","id",id));

        return ResponseEntity.ok(assembler.toModel(modelo));
    }

    // ✅ GET BY SISTEMA
    @Operation(summary = "Buscar Modelos por Sistema RPG")
    @GetMapping("/sistema/{sistema}")
    public ResponseEntity<PagedModel<EntityModel<ModeloFicha>>> getBySistema(
            @PathVariable String sistema,
            @ParameterObject Pageable pageable) {

        var modelos = repository.findBySistemaRPG(sistema, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(modelos, assembler)
        );
    }

    // ✅ CREATE
    @Operation(summary = "Criar Modelo de Ficha")
    @PostMapping
    public ResponseEntity<EntityModel<ModeloFicha>> create(
            @RequestBody ModeloFicha modelo) {

        // optional rule: avoid duplicate system (if you want)
        if (repository.existsBySistemaRPG(modelo.getSistemaRPG())) {
            throw new RPGAlreadyExistsException("ModeloFicha","sistemaRPG");
        }

        repository.save(modelo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(modelo));
    }

    // ✅ UPDATE
    @Operation(summary = "Atualizar Modelo de Ficha")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloFicha>> update(
            @PathVariable Long id,
            @RequestBody ModeloFicha updated) {

        return repository.findById(id).map(modelo -> {

            modelo.setSistemaRPG(updated.getSistemaRPG());
            modelo.setFields(updated.getFields());

            repository.save(modelo);

            return ResponseEntity.ok(assembler.toModel(modelo));

        }).orElseGet(() -> {

            repository.save(updated);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(assembler.toModel(updated));
        });
    }

    // ✅ DELETE
    @Operation(summary = "Deletar Modelo de Ficha")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        var modelo = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("ModeloFicha","id",id));

        repository.delete(modelo);

        return ResponseEntity.noContent().build();
    }
}