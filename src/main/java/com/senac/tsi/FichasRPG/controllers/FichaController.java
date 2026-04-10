package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.domains.ficha.Ficha;
import com.senac.tsi.FichasRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.FichasRPG.exceptions.FichaNotFoundException;
import com.senac.tsi.FichasRPG.repositories.FichaRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Fichas")
@RestController
@RequestMapping("/fichas")
public class FichaController {

    private final FichaRepositorio repository;
    private final PagedResourcesAssembler<Ficha> assembler;

    public FichaController(FichaRepositorio repository,
                           PagedResourcesAssembler<Ficha> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    @Operation(summary = "Listar todas as Fichas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HabilidadesFicha.class)))
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Ficha>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(assembler.toModel(repository.findAll(pageable)));
    }



    @Operation(summary = "Buscar Ficha por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ficha encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ficha não encontradas", content = @Content)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Ficha> getById(@PathVariable Long id) throws FichaNotFoundException {
        var ficha = repository.findById(id)
                .orElseThrow(() -> new FichaNotFoundException("Ficha" + id + "não encontrada"));

        return EntityModel.of(ficha,
                linkTo(methodOn(FichaController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(FichaController.class).getAll(Pageable.unpaged())).withRel("fichas"));
    }


    @Operation(summary = "Criar novas Ficha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ficha criadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Ficha> create(@RequestBody Ficha ficha) {
        repository.save(ficha);
        return ResponseEntity.created(URI.create("/fichas/" + ficha.getId())).body(ficha);
    }


    @Operation(summary = "Atualizar Ficha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ficha atualizadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "404", description = "Ficha não encontradas", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Ficha> update(@PathVariable Long id, @RequestBody Ficha updated) throws FichaNotFoundException {

        return repository.findById(id).map(ficha -> {
            ficha.setNomeJogador(updated.getNomeJogador());
            ficha.setInvestigador(updated.getInvestigador());
            ficha.setAtributos(updated.getAtributos());
            ficha.setStatus(updated.getStatus());
            ficha.setHabilidades(updated.getHabilidades());
            ficha.setHistorico(updated.getHistorico());
            ficha.setItensEDinheiro(updated.getItensEDinheiro());
            ficha.setCompanheiros(updated.getCompanheiros());

            return ResponseEntity.ok(repository.save(ficha));
        }).orElseThrow(() -> new FichaNotFoundException("Ficha" + id + "não encontrada"));
    }


    @Operation(summary = "Ficha Atributos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ficha deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ficha não encontradas", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws FichaNotFoundException {
        if (!repository.existsById(id))
            throw new FichaNotFoundException("Ficha" + id + "não encontrada");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}