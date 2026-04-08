package com.senac.tsi.CallOfCthulhuRPG.controllers;

import com.senac.tsi.CallOfCthulhuRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.personagens.Investigador;
import com.senac.tsi.CallOfCthulhuRPG.exceptions.InvestigadorNotFoundException;
import com.senac.tsi.CallOfCthulhuRPG.repositories.InvestigadorFichaRepositorio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/investigador")
public class InvestigadorController {

    private final InvestigadorFichaRepositorio repository;
    private final PagedResourcesAssembler<Investigador> assembler;

    public InvestigadorController(InvestigadorFichaRepositorio repository,
                                  PagedResourcesAssembler<Investigador> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Tag(name = "Investigador")
    @Operation(summary = "Listar todos os investigadores")
    @ApiResponse(responseCode = "200", description = "Investigadores retornados com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HabilidadesFicha.class)))
    @GetMapping("/Investigador")
    public ResponseEntity<PagedModel<EntityModel<Investigador>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(assembler.toModel(repository.findAll(pageable)));
    }

    @Tag(name = "Investigador")
    @Operation(summary = "Buscar Investigador por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investigador encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Investigador não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public EntityModel<Investigador> getById(@PathVariable Long id) throws InvestigadorNotFoundException {
        var entity = repository.findById(id)
                .orElseThrow(() -> new InvestigadorNotFoundException("Investigador " + id + "  não encontrada"));

        return EntityModel.of(entity);
    }

    @Tag(name = "Investigador")
    @Operation(summary = "Criar novas Investigador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Investigador criadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Investigador> create(@RequestBody Investigador entity) {
        repository.save(entity);
        return ResponseEntity.created(URI.create("/investigadores/" + entity.getId())).body(entity);
    }

    @Tag(name = "Investigador")
    @Operation(summary = "Atualizar Investigador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investigador atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "404", description = "Investigador não encontrado", content = @Content)
    })
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

    @Tag(name = "Investigador")
    @Operation(summary = "Deletar Investigador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Investigador deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Investigador não encontrado", content = @Content)
    })
    @DeleteMapping("/Investigador/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws InvestigadorNotFoundException {
        if (!repository.existsById(id))
            throw new InvestigadorNotFoundException("Investigador " + id + "  não encontrada");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}