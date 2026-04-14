package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.assemblers.TagAssembler;
import com.senac.tsi.FichasRPG.domains.tags.Tag;
import com.senac.tsi.FichasRPG.exceptions.GlobalExceptionHandler;
import com.senac.tsi.FichasRPG.exceptions.RpgValidationException;
import com.senac.tsi.FichasRPG.exceptions.TagNotFoundException;
import com.senac.tsi.FichasRPG.repositories.TagsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags")
@RequestMapping("/tags")
public class TagsController {

    private final TagsRepository repository;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;
    private final TagAssembler tagAssembler;

    TagsController(TagsRepository repository, PagedResourcesAssembler<Tag> pagedResourcesAssembler, TagAssembler tagAssembler) {
        this.repository = repository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.tagAssembler = tagAssembler;

    }

    @Operation(summary = "Listar todas as Tags",
            description = "Retorna uma lista com todas as Tags existentes no sistema")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Tag>>> getAll(@ParameterObject Pageable pageable) {
        var tags = repository.findAll(pageable);

        PagedModel<EntityModel<Tag>> pagedModelTag = pagedResourcesAssembler.toModel(tags, tagAssembler);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pagedModelTag);
    }


    @Operation(summary = "Pegar uma Tag pelo id",
            description = "Retorna uma lista com todas as Tags existentes no sistema")
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
    public ResponseEntity<EntityModel<Tag>> getTagById(@PathVariable(name = "id") long id){
        var tag = repository.findById(id).orElseThrow(
                ()-> new TagNotFoundException(id)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagAssembler.toModel(tag));
    }



    @Operation(summary = "Pegar uma Tag pelo nome",
            description = "Retorna uma Tag de acordo com o nome da tag")
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
    @GetMapping("/{nome}")
    public ResponseEntity<EntityModel<Tag>> getTagByName(@PathVariable(name = "nome") String nome){
        var tag = repository.findByName(nome).orElseThrow(
                ()-> new RuntimeException("Tag não encontrada")
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagAssembler.toModel(tag));
    }



    @Operation(summary = "Criar uma nova Tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Tag criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Tag.class))),
            @ApiResponse(responseCode = "400",description = "Input invalido",
                    content = @Content),
            @ApiResponse(responseCode = "400",description = "Input invalido",
                    content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<EntityModel<Tag>> createTag(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Tag to create", required = true,
                  content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = Tag.class),
            examples = @ExampleObject(value = "{ \"nomeTag\": }")))
        @RequestBody @Valid Tag novaTag){
        if (!repository.existsByName(novaTag.getNomeTag())){
            repository.save(novaTag);
        }else{throw new RuntimeException();}//Exception a ser criada - 409 - already exists

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagAssembler.toModel(novaTag));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable(name = "id") Long id,@RequestBody Tag updatedTag){
        Tag uptadedTag  = repository.findById(id).map(
                tag -> {tag.setNomeTag(tag.getNomeTag());}
            ).orElseThrow(() -> new RuntimeException("Não existe essa tag"));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updatedTag);

    }

}



