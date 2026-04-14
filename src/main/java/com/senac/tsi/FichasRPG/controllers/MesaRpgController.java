package com.senac.tsi.FichasRPG.controllers;


import com.senac.tsi.FichasRPG.assemblers.JogadorAssembler;
import com.senac.tsi.FichasRPG.domains.mesa.Jogador;
import com.senac.tsi.FichasRPG.domains.mesa.MesaRPG;
import com.senac.tsi.FichasRPG.domains.mesa.Papel;
import com.senac.tsi.FichasRPG.exceptions.RPGAlreadyExistsException;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.assemblers.MesaAssembler;

import com.senac.tsi.FichasRPG.repositories.JogadorRepository;
import com.senac.tsi.FichasRPG.repositories.MesasRPGRepository;
import com.senac.tsi.FichasRPG.repositories.TagRepository;
import com.senac.tsi.FichasRPG.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/mesas")
@io.swagger.v3.oas.annotations.tags.Tag(
        name = "Mesas RPG",
        description = "Operações relacionadas às mesas de RPG"
)
public class MesaRpgController {

    private final MesasRPGRepository repository;
    private final TagRepository tagRepository;
    private final JogadorRepository jogadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagedResourcesAssembler<MesaRPG> pagedAssembler;
    private final MesaAssembler assembler;
    private final JogadorAssembler jogadorAssembler;

    public MesaRpgController(MesasRPGRepository repository,
                             PagedResourcesAssembler<MesaRPG> pagedAssembler,
                             MesaAssembler assembler,
                             TagRepository tagsRepository,
                             JogadorRepository jogadorRepository,
                             UsuarioRepository usuarioRepository,
                             JogadorAssembler jogadorAssembler) {
        this.repository = repository;
        this.pagedAssembler = pagedAssembler;
        this.assembler = assembler;
        this.tagRepository = tagsRepository;
        this.jogadorRepository = jogadorRepository;
        this.usuarioRepository = usuarioRepository;
        this.jogadorAssembler = jogadorAssembler;
    }

    // 🔥 GET ALL
    @Operation(summary = "Listar mesas", description = "Retorna uma lista paginada de mesas")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<MesaRPG>>> getAll(@ParameterObject Pageable pageable) {
        var mesas = repository.findAll(pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(mesas, assembler));
    }

    // 🔥 GET BY ID
    @Operation(summary = "Buscar mesa por ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mesa encontrada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mesa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<MesaRPG>> getById(
            @io.swagger.v3.oas.annotations.Parameter(description = "ID da mesa", example = "1")
            @PathVariable Long id) {

        var mesa = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",id));

        return ResponseEntity.ok(assembler.toModel(mesa));
    }

    // 🔥 GET BY TAG ID
    @Operation(summary = "Listar mesas por ID da tag")
    @GetMapping("/tags/id/{tagId}")
    public ResponseEntity<PagedModel<EntityModel<MesaRPG>>> getByTagId(
            @io.swagger.v3.oas.annotations.Parameter(example = "1")
            @PathVariable Long tagId,
            @ParameterObject Pageable pageable) {

        var mesas = repository.findByTags_Id(tagId, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(mesas, assembler));
    }

    // 🔥 GET BY TAG NAME
    @Operation(summary = "Listar mesas por nome da tag")
    @GetMapping("/tags/nome/{tagName}")
    public ResponseEntity<PagedModel<EntityModel<MesaRPG>>> getByTagName(
            @io.swagger.v3.oas.annotations.Parameter(example = "fantasia")
            @PathVariable String tagName,
            @ParameterObject Pageable pageable) {

        var mesas = repository.findByTags_NomeTag(tagName, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(mesas, assembler));
    }

    // 🔥 CREATE
    @Operation(summary = "Criar mesa")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Mesa criada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<MesaRPG>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da mesa",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MesaRPG.class)
                    )
            )
            @RequestBody MesaRPG mesa) {

        repository.save(mesa);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assembler.toModel(mesa));
    }

    // 🔥 UPDATE
    @Operation(summary = "Atualizar mesa")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mesa atualizada"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mesa não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<MesaRPG>> update(
            @io.swagger.v3.oas.annotations.Parameter(example = "1")
            @PathVariable Long id,
            @RequestBody MesaRPG updated) {

        return repository.findById(id).map(mesa -> {

            mesa.setNome(updated.getNome());

            repository.save(mesa);
            return ResponseEntity.ok(assembler.toModel(mesa));

        }).orElseThrow(() -> new RPGNotFoundException("Mesa","id",id));
    }

    // 🔥 ADD TAG
    @Operation(summary = "Adicionar tag à mesa")
    @PutMapping("/{mesaId}/tags/{tagId}")
    public ResponseEntity<EntityModel<MesaRPG>> addTag(
            @PathVariable Long mesaId,
            @PathVariable Long tagId) {

        var mesa = repository.findById(mesaId)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",mesaId));

        var tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RPGNotFoundException("Tag","id",tagId));

        if (!mesa.getTags().contains(tag)) {
            mesa.addTag(tag);
            repository.save(mesa);
        }

        return ResponseEntity.ok(assembler.toModel(mesa));
    }

    // 🔥 ADD JOGADOR
    @Operation(summary = "Adicionar jogador à mesa")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Jogador adicionado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Já existe um MASTER")
    })
    @PutMapping("/{mesaId}/jogadores/{usuarioId}")
    public ResponseEntity<EntityModel<Jogador>> adicionarJogador(
            @PathVariable Long mesaId,
            @PathVariable Long usuarioId,
            @io.swagger.v3.oas.annotations.Parameter(description = "Papel do jogador", example = "PLAYER")
            @RequestParam(defaultValue = "PLAYER") Papel papel) {

        var mesa = repository.findById(mesaId)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",mesaId));

        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RPGNotFoundException("Usuario","id",usuarioId));

        var jogadorOpt = jogadorRepository
                .findByMesa_IdAndUsuario_Id(mesaId, usuarioId);

        if (jogadorOpt.isPresent()) {
            return ResponseEntity.ok(jogadorAssembler.toModel(jogadorOpt.get()));
        }

        if (papel == Papel.MASTER &&
                jogadorRepository.existsByMesa_IdAndPapel(mesaId, Papel.MASTER)) {
            throw new RPGAlreadyExistsException("Mesa","master");
        }

        Jogador jogador = new Jogador(usuario, mesa, papel);
        jogadorRepository.save(jogador);

        return ResponseEntity.ok(jogadorAssembler.toModel(jogador));
    }

    // 🔥 REMOVE TAG
    @Operation(summary = "Remover tag da mesa")
    @DeleteMapping("/{mesaId}/tags/{tagId}")
    public ResponseEntity<Void> removeTag(
            @PathVariable Long mesaId,
            @PathVariable Long tagId) {

        var mesa = repository.findById(mesaId)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",mesaId));

        var tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RPGNotFoundException("Tag","id",tagId));

        if (mesa.getTags().contains(tag)) {
            mesa.removeTag(tag);
            repository.save(mesa);
        }

        return ResponseEntity.noContent().build();
    }

    // 🔥 REMOVE JOGADOR
    @Operation(summary = "Remover jogador da mesa")
    @DeleteMapping("/{mesaId}/jogadores/{usuarioId}")
    public ResponseEntity<Void> removerJogador(
            @PathVariable Long mesaId,
            @PathVariable Long usuarioId) {

        var jogador = jogadorRepository
                .findByMesa_IdAndUsuario_Id(mesaId, usuarioId)
                .orElseThrow(() -> new RPGNotFoundException("Jogador","usuario/mesa",usuarioId));

        jogadorRepository.delete(jogador);

        return ResponseEntity.noContent().build();
    }

    // 🔥 DELETE MESA
    @Operation(summary = "Deletar mesa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        var mesa = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Mesa","id",id));

        repository.delete(mesa);
        return ResponseEntity.noContent().build();
    }
}