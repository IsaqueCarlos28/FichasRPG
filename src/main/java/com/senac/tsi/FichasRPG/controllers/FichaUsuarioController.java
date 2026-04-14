package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.assemblers.FichaUsuarioAssembler;
import com.senac.tsi.FichasRPG.domains.usuario.FichaUsuario;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.FichaUsuarioRepository;

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
@RequestMapping("/fichas-usuarios")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Fichas de Usuario")
public class FichaUsuarioController {

    private final FichaUsuarioRepository repository;
    private final FichaUsuarioAssembler assembler;
    private final PagedResourcesAssembler<FichaUsuario> pagedAssembler;

    public FichaUsuarioController(FichaUsuarioRepository repository,
                                  PagedResourcesAssembler<FichaUsuario> pagedAssembler,
                                  FichaUsuarioAssembler assembler) {
        this.repository = repository;
        this.pagedAssembler = pagedAssembler;
        this.assembler = assembler;
    }

    // ✅ GET ALL (all users)
    @Operation(summary = "Listar todas as fichas de usuários")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FichaUsuario>>> getAll(
            @ParameterObject Pageable pageable) {

        var fichas = repository.findAll(pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(fichas, assembler)
        );
    }

    // ✅ GET BY ID
    @Operation(summary = "Buscar ficha por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ficha encontrada",
                    content = @Content(schema = @Schema(implementation = FichaUsuario.class))),
            @ApiResponse(responseCode = "404", description = "Ficha não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<FichaUsuario>> getById(@PathVariable Long id) {

        var ficha = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("FichaUsuario","id",id));

        return ResponseEntity.ok(assembler.toModel(ficha));
    }

    // ✅ GET BY USUARIO
    @Operation(summary = "Listar fichas por usuário")
    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<PagedModel<EntityModel<FichaUsuario>>> getByUsuario(
            @PathVariable Long usuarioId,
            @ParameterObject Pageable pageable) {

        var fichas = repository.findByUsuario_Id(usuarioId, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(fichas, assembler)
        );
    }

    // ✅ GET BY MODELO FICHA
    @Operation(summary = "Listar fichas por modelo de ficha")
    @GetMapping("/modelos/{modeloId}")
    public ResponseEntity<PagedModel<EntityModel<FichaUsuario>>> getByModelo(
            @PathVariable Long modeloId,
            @ParameterObject Pageable pageable) {

        var fichas = repository.findByModeloFicha_Id(modeloId, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(fichas, assembler)
        );
    }

    // ✅ CREATE
    @Operation(summary = "Criar ficha de usuário")
    @PostMapping
    public ResponseEntity<EntityModel<FichaUsuario>> create(
            @RequestBody FichaUsuario ficha) {

        repository.save(ficha);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(ficha));
    }

    // ✅ UPDATE
    @Operation(summary = "Atualizar ficha de usuário")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FichaUsuario>> update(
            @PathVariable Long id,
            @RequestBody FichaUsuario updated) {

        return repository.findById(id).map(ficha -> {

            ficha.setNomePersonagem(updated.getNomePersonagem());
            ficha.setCampos(updated.getCampos());
            ficha.setUsuario(updated.getUsuario());

            repository.save(ficha);

            return ResponseEntity.ok(assembler.toModel(ficha));

        }).orElseGet(() -> {

            repository.save(updated);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(assembler.toModel(updated));
        });
    }

    // ✅ DELETE
    @Operation(summary = "Deletar ficha de usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        var ficha = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("FichaUsuario","id",id));

        repository.delete(ficha);

        return ResponseEntity.noContent().build();
    }
}