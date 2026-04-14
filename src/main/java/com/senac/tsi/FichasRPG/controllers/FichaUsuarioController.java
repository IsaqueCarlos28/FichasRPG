package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.assemblers.FichaUsuarioAssembler;
import com.senac.tsi.FichasRPG.domains.usuario.FichaUsuario;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.FichaUsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Tag(name = "Modelo Fichas / Sistemas")
@RestController
@RequestMapping("/fichas-modelos")
public class FichaUsuarioController {

    private final FichaUsuarioRepository repository;
    private final FichaUsuarioAssembler fichaAssembler;
    private final PagedResourcesAssembler<FichaUsuario> pagedResourcesAssembler;

    FichaUsuarioController(FichaUsuarioRepository repository,
                           PagedResourcesAssembler<FichaUsuario> pagedResourcesAssembler,
                           FichaUsuarioAssembler assembler) {
        this.repository = repository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.fichaAssembler = assembler;

    }

    @Operation(summary = "Listar todas as Tags",
            description = "Retorna uma lista com todas as Tags existentes no sistema")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FichaUsuario>>> getAll(@ParameterObject Pageable pageable) {
        var fichaUsuarios = repository.findAll(pageable);

        PagedModel<EntityModel<FichaUsuario>> pagedFichaUsuarios = pagedResourcesAssembler.toModel(fichaUsuarios, fichaAssembler);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pagedFichaUsuarios);
    }

    @Operation(summary = "Pegar Modelo de Ficha pelo id",
            description = "Retorna uma lista com todas os Modelo de Ficha existentes no sistema")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",description = "Tag retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FichaUsuario.class))),//Input invalido
            @ApiResponse(responseCode = "404",description = "Tag não encontrada",
                    content = @Content),//Not Found
            @ApiResponse(responseCode = "400",description = "Input errado",
                    content = @Content)//Input invalido
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<FichaUsuario>> getFichaUsuarioById(@PathVariable(name = "id") long id){
        var fichaUsuario = repository.findById(id).orElseThrow(
                ()-> new RPGNotFoundException("Ficha de Usuario","id",id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fichaAssembler.toModel(fichaUsuario));
    }

}