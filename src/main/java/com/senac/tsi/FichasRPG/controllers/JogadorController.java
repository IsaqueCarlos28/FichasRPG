package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.domains.mesa.Jogador;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.JogadorRepository;
import com.senac.tsi.FichasRPG.assemblers.JogadorAssembler;

import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/jogadores")
@io.swagger.v3.oas.annotations.tags.Tag(
        name = "Jogadores",
        description = "Operações relacionadas aos jogadores nas mesas de RPG"
)
public class JogadorController {

    private final JogadorRepository repository;
    private final PagedResourcesAssembler<Jogador> pagedAssembler;
    private final JogadorAssembler assembler;

    public JogadorController(JogadorRepository repository,
                             PagedResourcesAssembler<Jogador> pagedAssembler,
                             JogadorAssembler assembler) {
        this.repository = repository;
        this.pagedAssembler = pagedAssembler;
        this.assembler = assembler;
    }

    // 🔥 jogadores por mesa
    @Operation(
            summary = "Listar jogadores por mesa",
            description = "Retorna uma lista paginada de jogadores pertencentes a uma mesa"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mesa não encontrada")
    })
    @GetMapping("/mesas/{mesaId}")
    public ResponseEntity<PagedModel<EntityModel<Jogador>>> getByMesa(
            @io.swagger.v3.oas.annotations.Parameter(description = "ID da mesa", example = "1")
            @PathVariable Long mesaId,
            @ParameterObject Pageable pageable) {

        var jogadores = repository.findByMesa_Id(mesaId, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(jogadores, assembler));
    }

    // 🔥 jogadores por usuário
    @Operation(
            summary = "Listar jogadores por usuário",
            description = "Retorna uma lista paginada de participações de um usuário em mesas"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<PagedModel<EntityModel<Jogador>>> getByUsuario(
            @io.swagger.v3.oas.annotations.Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long usuarioId,
            @ParameterObject Pageable pageable) {

        var jogadores = repository.findByUsuario_Id(usuarioId, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(jogadores, assembler));
    }

    // 🔥 GET BY ID
    @Operation(
            summary = "Buscar jogador por ID",
            description = "Retorna um jogador específico pelo ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Jogador encontrado",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Jogador.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Jogador não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Jogador>> getById(
            @io.swagger.v3.oas.annotations.Parameter(description = "ID do jogador", example = "1")
            @PathVariable Long id) {

        var jogador = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Jogador","id",id));

        return ResponseEntity.ok(assembler.toModel(jogador));
    }

    // 🔥 CREATE
    @Operation(
            summary = "Criar jogador",
            description = "Adiciona um jogador a uma mesa"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Jogador criado com sucesso",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Jogador.class)
                    )),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Jogador>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do jogador a ser criado",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Jogador.class)
                    )
            )
            @RequestBody Jogador jogador) {

        repository.save(jogador);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(jogador));
    }

    // 🔥 UPDATE
    @Operation(
            summary = "Atualizar jogador",
            description = "Atualiza os dados de um jogador existente"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Jogador atualizado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Jogador não encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Jogador>> update(
            @io.swagger.v3.oas.annotations.Parameter(description = "ID do jogador", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do jogador",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Jogador.class)
                    )
            )
            @RequestBody Jogador updated) {

        return repository.findById(id).map(jogador -> {

            jogador.setUsuario(updated.getUsuario());
            jogador.setMesa(updated.getMesa());
            jogador.setFicha(updated.getFicha());
            jogador.setPapel(updated.getPapel());

            repository.save(jogador);
            return ResponseEntity.ok(assembler.toModel(jogador));

        }).orElseThrow(() -> new RPGNotFoundException("Jogador","id",id));
    }

    // 🔥 SAIR DA MESA
    @Operation(
            summary = "Remover jogador da mesa",
            description = "Remove a associação de um usuário com uma mesa (sair da mesa)"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Jogador removido com sucesso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Jogador não encontrado")
    })
    @DeleteMapping("/mesas/{mesaId}/jogadores/{usuarioId}")
    public ResponseEntity<Void> sairDaMesa(
            @io.swagger.v3.oas.annotations.Parameter(description = "ID da mesa", example = "1")
            @PathVariable Long mesaId,
            @io.swagger.v3.oas.annotations.Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long usuarioId) {

        var jogador = repository
                .findByMesa_IdAndUsuario_Id(mesaId, usuarioId)
                .orElseThrow(() -> new RPGNotFoundException("Jogador","usuario/mesa",usuarioId));

        repository.delete(jogador);

        return ResponseEntity.noContent().build();
    }

    // 🔥 DELETE
    @Operation(
            summary = "Deletar jogador",
            description = "Remove um jogador pelo ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Jogador deletado com sucesso"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Jogador não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @io.swagger.v3.oas.annotations.Parameter(description = "ID do jogador", example = "1")
            @PathVariable Long id) {

        var jogador = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Jogador","id",id));

        repository.delete(jogador);
        return ResponseEntity.noContent().build();
    }
}