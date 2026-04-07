package com.senac.tsi.CallOfCthulhuRPG.controllers;

import com.senac.tsi.CallOfCthulhuRPG.repositories.HabilidadeFichaRepositorio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.tsi.CallOfCthulhuRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.CallOfCthulhuRPG.exceptions.HabilidadesNotFoundException;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/habilidades")
public class HabilidadesFichaController {

    private final HabilidadeFichaRepositorio repository;
    private final PagedResourcesAssembler<HabilidadesFicha> assembler;

    public HabilidadesFichaController(HabilidadeFichaRepositorio repository,
                                      PagedResourcesAssembler<HabilidadesFicha> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todas habilidades")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HabilidadesFicha.class)))
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<HabilidadesFicha>>> getAll(@ParameterObject Pageable pageable) {
        var page = repository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(page));
    }

    @Operation(summary = "Buscar habilidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidades encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Habilidades não encontradas", content = @Content)
    })
    @GetMapping("/{id}")
    public EntityModel<HabilidadesFicha> getById(@PathVariable Long id) throws HabilidadesNotFoundException {

        var entity = repository.findById(id)
                .orElseThrow(() -> new HabilidadesNotFoundException("Habilidade " + id + " não encontrada"));

        return EntityModel.of(entity,
                linkTo(methodOn(HabilidadesFichaController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(HabilidadesFichaController.class).getAll(Pageable.unpaged())).withRel("habilidades"));
    }

    @Operation(summary = "Criar novas habilidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Habilidades criadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<HabilidadesFicha> create(@RequestBody HabilidadesFicha entity) {
        repository.save(entity);
        return ResponseEntity
                .created(URI.create("/habilidades/" + entity.getId()))
                .body(entity);
    }

    @Operation(summary = "Atualizar Habilidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidades atualizadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "404", description = "Habilidades não encontradas", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<HabilidadesFicha> update(@PathVariable Long id,
                                                   @RequestBody HabilidadesFicha updated) throws HabilidadesNotFoundException {

        return repository.findById(id).map(entity -> {

            entity.setPericiasInvestigador(updated.getPericiasInvestigador());
            entity.setPericiasOcupacional(updated.getPericiasOcupacional());
            entity.setArmas(updated.getArmas());

            return ResponseEntity.ok(repository.save(entity));

        }).orElseThrow(() -> new HabilidadesNotFoundException("Habilidade " + id + " não encontrada"));
    }

    @Operation(summary = "Deletar Habilidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Habilidades deletadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Habilidades não encontradas", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws HabilidadesNotFoundException {

        if (!repository.existsById(id))
            throw new HabilidadesNotFoundException("Habilidade " + id + " não encontrada");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}