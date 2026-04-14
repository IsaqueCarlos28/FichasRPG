package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.domains.usuario.Usuario;
import com.senac.tsi.FichasRPG.exceptions.RPGAlreadyExistsException;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.UsuarioRepository;
import com.senac.tsi.FichasRPG.assemblers.UsuarioAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários do sistema")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PagedResourcesAssembler<Usuario> pagedAssembler;
    private final UsuarioAssembler assembler;

    public UsuarioController(UsuarioRepository repository,
                             PagedResourcesAssembler<Usuario> pagedAssembler,
                             UsuarioAssembler assembler) {
        this.repository = repository;
        this.pagedAssembler = pagedAssembler;
        this.assembler = assembler;
    }

    // 🔥 GET ALL
    @Operation(summary = "Listar usuários", description = "Retorna uma lista paginada de todos os usuários")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Usuario>>> getAll(@ParameterObject Pageable pageable) {
        var usuarios = repository.findAll(pageable);
        PagedModel<EntityModel<Usuario>> pagedModelUsuarios = pagedAssembler.toModel(usuarios, assembler);
        return ResponseEntity.ok(pagedModelUsuarios);
    }

    // 🔥 GET BY ID
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> getById(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id) {

        var usuario = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Usuario","id",id));

        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    // 🔥 CREATE
    @Operation(summary = "Criar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo usuário",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Usuario.class))
            )
            @RequestBody @Valid Usuario usuario) {

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RPGAlreadyExistsException("Usuario","email");
        }

        repository.save(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(usuario));
    }

    // 🔥 UPDATE
    @Operation(summary = "Atualizar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> update(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id,
            @RequestBody Usuario updated) {

        return repository.findById(id).map(usuario -> {
            usuario.setNome(updated.getNome());
            usuario.setEmail(updated.getEmail());

            repository.save(usuario);
            return ResponseEntity.ok(assembler.toModel(usuario));

        }).orElseThrow(() -> new RPGNotFoundException("Usuario","id",id));
    }

    // 🔥 DELETE
    @Operation(summary = "Deletar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id) {

        var usuario = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Usuario","id",id));

        repository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}