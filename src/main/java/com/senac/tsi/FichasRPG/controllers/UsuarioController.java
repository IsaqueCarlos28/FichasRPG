package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.domains.tags.Tag;
import com.senac.tsi.FichasRPG.domains.usuario.Usuario;
import com.senac.tsi.FichasRPG.exceptions.RPGAlreadyExistsException;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.UsuarioRepository;
import com.senac.tsi.FichasRPG.assemblers.UsuarioAssembler;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PagedResourcesAssembler<Usuario> pagedAssembler;
    private final UsuarioAssembler assembler;

    public UsuarioController(UsuarioRepository repository,
                             PagedResourcesAssembler<Usuario> pagedAssembler,
                             UsuarioAssembler assembler) {
        this.repository = repository;
        this.pagedAssembler = pagedAssembler;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Usuario>>> getAll(@ParameterObject Pageable pageable) {
        var usuarios = repository.findAll(pageable);
        PagedModel<EntityModel<Usuario>> pagedModelUsuarios = pagedAssembler.toModel(usuarios, assembler);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pagedModelUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> getById(@PathVariable Long id) {
        var usuario = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Usuario","id",id));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(assembler.toModel(usuario));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> create(@RequestBody @Valid Usuario usuario) {

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RPGAlreadyExistsException("Usuario","email");
        }

        repository.save(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> update(@PathVariable Long id,
                                                       @RequestBody Usuario updated) {

        return repository.findById(id).map(usuario -> {
            usuario.setNome(updated.getNome());
            usuario.setEmail(updated.getEmail());

            repository.save(usuario);
            return ResponseEntity.ok(assembler.toModel(usuario));

        }).orElseThrow(() -> new RPGNotFoundException("Usuario","id",id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var usuario = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Usuario","id",id));

        repository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}