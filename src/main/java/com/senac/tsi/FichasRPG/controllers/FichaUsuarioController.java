package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.assemblers.FichaUsuarioAssembler;
import com.senac.tsi.FichasRPG.domains.usuario.FichaUsuario;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.FichaUsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@io.swagger.v3.oas.annotations.tags.Tag(
        name = "Fichas de Usuario",
        description = "Operações relacionadas às fichas de personagens dos usuários"
)
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

    // ✅ GET ALL
    @Operation(
            summary = "Listar todas as fichas de usuários",
            description = "Retorna uma lista paginada de fichas de usuários"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FichaUsuario>>> getAll(
            @ParameterObject Pageable pageable) {

        var fichas = repository.findAll(pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(fichas, assembler)
        );
    }

    // ✅ GET BY ID
    @Operation(
            summary = "Buscar ficha por ID",
            description = "Retorna uma ficha de usuário específica pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ficha encontrada",
                    content = @Content(schema = @Schema(implementation = FichaUsuario.class))),
            @ApiResponse(responseCode = "404", description = "Ficha não encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<FichaUsuario>> getById(
            @Parameter(description = "ID da ficha", example = "1")
            @PathVariable Long id) {

        var ficha = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("FichaUsuario","id",id));

        return ResponseEntity.ok(assembler.toModel(ficha));
    }

    // ✅ GET BY USUARIO
    @Operation(
            summary = "Listar fichas por usuário",
            description = "Retorna fichas associadas a um usuário específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<PagedModel<EntityModel<FichaUsuario>>> getByUsuario(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long usuarioId,
            @ParameterObject Pageable pageable) {

        var fichas = repository.findByUsuario_Id(usuarioId, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(fichas, assembler)
        );
    }

    // ✅ GET BY MODELO
    @Operation(
            summary = "Listar fichas por modelo de ficha",
            description = "Retorna fichas baseadas em um modelo de ficha"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    @GetMapping("/modelos/{modeloId}")
    public ResponseEntity<PagedModel<EntityModel<FichaUsuario>>> getByModelo(
            @Parameter(description = "ID do modelo de ficha", example = "1")
            @PathVariable Long modeloId,
            @ParameterObject Pageable pageable) {

        var fichas = repository.findByModeloFicha_Id(modeloId, pageable);

        return ResponseEntity.ok(
                pagedAssembler.toModel(fichas, assembler)
        );
    }

    // ✅ CREATE
    @Operation(
            summary = "Criar ficha de usuário",
            description = "Cria uma nova ficha de usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ficha criada com sucesso",
                    content = @Content(schema = @Schema(implementation = FichaUsuario.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<FichaUsuario>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da ficha a ser criada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FichaUsuario.class))
            )
            @RequestBody FichaUsuario ficha) {

        repository.save(ficha);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(ficha));
    }

    // ✅ UPDATE
    @Operation(
            summary = "Atualizar ficha de usuário",
            description = "Atualiza uma ficha existente ou cria uma nova caso não exista"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ficha atualizada"),
            @ApiResponse(responseCode = "201", description = "Ficha criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FichaUsuario>> update(
            @Parameter(description = "ID da ficha", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados da ficha",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FichaUsuario.class))
            )
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
    @Operation(
            summary = "Deletar ficha de usuário",
            description = "Remove uma ficha pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ficha deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ficha não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da ficha", example = "1")
            @PathVariable Long id) {

        var ficha = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("FichaUsuario","id",id));

        repository.delete(ficha);

        return ResponseEntity.noContent().build();
    }
}
