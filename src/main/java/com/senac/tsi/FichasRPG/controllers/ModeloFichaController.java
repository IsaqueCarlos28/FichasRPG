package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.assemblers.ModeloFichaAssembler;
import com.senac.tsi.FichasRPG.domains.modeloFicha.ModeloFicha;
import com.senac.tsi.FichasRPG.domains.tags.Tag;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.ModeloFichaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModeloFichaController {
    private final ModeloFichaRepository repository;
    private final PagedResourcesAssembler<ModeloFicha> pagedResourcesAssembler;
    private final ModeloFichaAssembler ModeloAssembler;

    public ModeloFichaController(ModeloFichaRepository repository,
                                 PagedResourcesAssembler<ModeloFicha> pagedResourcesAssembler,
                                 ModeloFichaAssembler assembler) {
        this.repository = repository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.ModeloAssembler = assembler;
    }

    @Operation(summary = "Pegar Modelo de Ficha pelo id",
            description = "Retorna uma lista com todas os Modelo de Ficha existentes no sistema")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",description = "Tag retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Tag.class))),//Input invalido
            @ApiResponse(responseCode = "404",description = "Tag não encontrada",
                    content = @Content),//Not Found
            @ApiResponse(responseCode = "400",description = "Input errado",
                    content = @Content)//Input invalido
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloFicha>> getModeloFichaById(@PathVariable(name = "id") long id){
        var modeloFicha = repository.findById(id).orElseThrow(
                ()-> new RPGNotFoundException("Modelo de Ficha","id",id)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ModeloAssembler.toModel(modeloFicha));
    }
}
