package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.assemblers.ModeloFichaAssembler;
import com.senac.tsi.FichasRPG.domains.modeloFicha.ModeloFicha;
import com.senac.tsi.FichasRPG.exceptions.RPGAlreadyExistsException;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.ModeloFichaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modelos-ficha")
@Tag(name = "Modelo Ficha", description = "Operações relacionadas aos modelos base das fichas de RPG")
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


    // 🔥 GET ALL
    @Operation(summary = "Listar modelos", description = "Retorna uma lista paginada de todos os modelos de ficha")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ModeloFicha>>> getAll(
            @ParameterObject Pageable pageable) {

        var modelos = repository.findAll(pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(modelos, assembler)
        );
    }

    // 🔥 GET BY ID
    @Operation(summary = "Buscar modelo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modelo encontrado"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloFicha>> getById(
            @Parameter(description = "ID do modelo", example = "1")
            @PathVariable Long id) {

        var modelo = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("ModeloFicha","id",id));

        return ResponseEntity.ok(assembler.toModel(modelo));
    }

    // 🔥 GET BY SISTEMA
    @Operation(summary = "Buscar modelos por sistema")
    @ApiResponse(responseCode = "200", description = "Modelos filtrados com sucesso")
    @GetMapping("/sistema/{sistema}")
    public ResponseEntity<PagedModel<EntityModel<ModeloFicha>>> getBySistema(
            @Parameter(description = "Nome do sistema", example = "D&D 5e")
            @PathVariable String sistema,
            @ParameterObject Pageable pageable) {

        var modelos = repository.findBySistemaRPG(sistema, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(modelos, assembler)
        );
    }

    // 🔥 CREATE
    @Operation(summary = "Criar modelo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Modelo criado"),
            @ApiResponse(responseCode = "409", description = "Sistema já cadastrado")
    })
    @PostMapping
    public ResponseEntity<EntityModel<ModeloFicha>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo modelo",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ModeloFicha.class))
            )
            @RequestBody ModeloFicha modelo) {

        if (repository.existsBySistemaRPG(modelo.getSistemaRPG())) {
            throw new RPGAlreadyExistsException("ModeloFicha","sistemaRPG");
        }

        repository.save(modelo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(modelo));
    }

    // 🔥 UPDATE
    @Operation(summary = "Atualizar modelo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelo atualizado"),
            @ApiResponse(responseCode = "201", description = "Modelo criado (Upsert)"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloFicha>> update(
            @Parameter(description = "ID do modelo a ser atualizado", example = "1")
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

    // 🔥 DELETE
    @Operation(summary = "Deletar modelo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Modelo removido"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        var modelo = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("ModeloFicha","id",id));

        repository.delete(modelo);

        return ResponseEntity.noContent().build();
    }
}