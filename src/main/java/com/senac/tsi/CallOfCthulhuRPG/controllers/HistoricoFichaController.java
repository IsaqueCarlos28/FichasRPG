package com.senac.tsi.CallOfCthulhuRPG.controllers;

import com.senac.tsi.CallOfCthulhuRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.CallOfCthulhuRPG.repositories.HistoricoFichaRepositorio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.senac.tsi.CallOfCthulhuRPG.domains.historico.HistoricoFicha;
import com.senac.tsi.CallOfCthulhuRPG.exceptions.HistoricoNotFoundException;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/historico")
public class HistoricoFichaController {

    private final HistoricoFichaRepositorio repository;
    private final PagedResourcesAssembler<HistoricoFicha> assembler;

    public HistoricoFichaController(HistoricoFichaRepositorio repository,
                                    PagedResourcesAssembler<HistoricoFicha> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Tag(name = "Historicos")
    @Operation(summary = "Listar todas habilidades")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HabilidadesFicha.class)))
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<HistoricoFicha>>> getAll(@ParameterObject Pageable pageable) {
        var page = repository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(page));
    }

    @Tag(name = "Historicos")
    @Operation(summary = "Buscar historico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidades encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Habilidades não encontradas", content = @Content)
    })
    @GetMapping("/{id}")
    public EntityModel<HistoricoFicha> getById(@PathVariable Long id) throws HistoricoNotFoundException {

        var entity = repository.findById(id)
                .orElseThrow(() -> new HistoricoNotFoundException("Historico " + id + "   não encontrado"));

        return EntityModel.of(entity,
                linkTo(methodOn(HistoricoFichaController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(HistoricoFichaController.class).getAll(Pageable.unpaged())).withRel("historicos"));
    }

    @Tag(name = "Historicos")
    @Operation(summary = "Criar novas historico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Historico criadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping("/Historico")
    public ResponseEntity<HistoricoFicha> create(@RequestBody HistoricoFicha entity) {
        repository.save(entity);
        return ResponseEntity
                .created(URI.create("/historicos/" + entity.getId()))
                .body(entity);
    }

    @Tag(name = "Historicos")
    @Operation(summary = "Atualizar Historico de personagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historico de personagem atualizadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "404", description = "Historico de personagem não encontradas", content = @Content)
    })
    @PutMapping("/Historico/{id}")
    public ResponseEntity<HistoricoFicha> update(@PathVariable Long id,
                                                 @RequestBody HistoricoFicha updated) throws HistoricoNotFoundException {

        return repository.findById(id).map(entity -> {

            entity.setDescricaoPessoais(updated.getDescricaoPessoais());
            entity.setIdeologias(updated.getIdeologias());
            entity.setPessoasImportantes(updated.getPessoasImportantes());
            entity.setItensValiosos(updated.getItensValiosos());
            entity.setTracos(updated.getTracos());
            entity.setTomosArcanos(updated.getTomosArcanos());
            entity.setEncontrosEstranhos(updated.getEncontrosEstranhos());
            entity.setMachucadosECicatrizes(updated.getMachucadosECicatrizes());
            entity.setFobiasEManias(updated.getFobiasEManias());

            return ResponseEntity.ok(repository.save(entity));

        }).orElseThrow(() -> new HistoricoNotFoundException("Historico " + id + "   não encontrado"));
    }

    @Tag(name = "Historicos")
    @Operation(summary = "Deletar Historico do Investigado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Historico deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Historico não encontradas", content = @Content)
    })
    @DeleteMapping("/Historico/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws HistoricoNotFoundException {

        if (!repository.existsById(id))
            throw new HistoricoNotFoundException("Historico " + id + "   não encontrado");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}