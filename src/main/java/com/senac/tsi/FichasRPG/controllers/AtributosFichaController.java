package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.domains.atributos.AtributosFicha;
import com.senac.tsi.FichasRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.FichasRPG.exceptions.AtributosNotFoundException;
import com.senac.tsi.FichasRPG.repositories.AtributosFichaRepositorio;
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

@Tag(name = "Atributos")
@RestController
@RequestMapping("/atributos/CallOfCthulhu")
public class AtributosFichaController {

    private final AtributosFichaRepositorio repository;
    private final PagedResourcesAssembler<AtributosFicha> assembler;

    public AtributosFichaController(AtributosFichaRepositorio repository,
                                    PagedResourcesAssembler<AtributosFicha> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(summary = "Buscar todos as Ficha de Atributos")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AtributosFicha>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(assembler.toModel(repository.findAll(pageable)));
    }

    @Operation(summary = "Buscar Atributos da Ficha por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atributos da Ficha encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Atributos da Ficha não encontradas", content = @Content)
    })
    @GetMapping("/{id}")
    public EntityModel<AtributosFicha> getById(@PathVariable Long id) throws AtributosNotFoundException {
        return EntityModel.of(
                repository.findById(id)
                        .orElseThrow(() -> new AtributosNotFoundException("Atributo " +  id + " nao encontrado"))
        );
    }

    @Operation(summary = "Criar novas Atributos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atributos criadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AtributosFicha> create(@RequestBody AtributosFicha entity) {
        repository.save(entity);
        return ResponseEntity.created(URI.create("/atributos/" + entity.getId())).body(entity);
    }


    @Operation(summary = "Atualizar Atributos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atributos atualizadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadesFicha.class))),
            @ApiResponse(responseCode = "404", description = "Atributos não encontradas", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AtributosFicha> update(@PathVariable Long id,
                                                 @RequestBody AtributosFicha updated) throws AtributosNotFoundException {

        return repository.findById(id).map(entity -> {
            entity.setAtributosPrimarios(updated.getAtributosPrimarios());
            entity.setSorte(updated.getSorte());
            entity.calcularFicha();

            return ResponseEntity.ok(repository.save(entity));
        }).orElseThrow(() -> new AtributosNotFoundException("Atributo " +  id + " nao encontrado"));
    }

    @Operation(summary = "Deletar Atributos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atributos deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atributos não encontradas", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws AtributosNotFoundException {
        if (!repository.existsById(id))
            throw new AtributosNotFoundException("Atributo " +  id + " nao encontrado");

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}