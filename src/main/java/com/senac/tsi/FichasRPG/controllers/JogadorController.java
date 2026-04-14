package com.senac.tsi.FichasRPG.controllers;

import com.senac.tsi.FichasRPG.domains.mesa.Jogador;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import com.senac.tsi.FichasRPG.repositories.JogadorRepository;
import com.senac.tsi.FichasRPG.assemblers.JogadorAssembler;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    private final JogadorRepository repository;
    private final PagedResourcesAssembler<Jogador> pagedAssembler;
    private final JogadorAssembler assembler;

    public JogadorController(JogadorRepository repository,
                             PagedResourcesAssembler<Jogador> pagedAssembler,
                             JogadorAssembler assembler) {
        this.repository = repository;
        this.pagedAssembler = pagedAssembler;
        this.assembler = assembler;
    }

    // 🔥 jogadores por mesa
    @GetMapping("/mesas/{mesaId}/")
    public ResponseEntity<PagedModel<EntityModel<Jogador>>> getByMesa(@PathVariable Long mesaId,
                                                                      @ParameterObject Pageable pageable) {
        var jogadores = repository.findByMesa_Id(mesaId, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(jogadores, assembler));
    }

    // 🔥 jogadores por usuario
    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<PagedModel<EntityModel<Jogador>>> getByUsuario(@PathVariable Long usuarioId,
                                                                         @ParameterObject Pageable pageable) {

        var jogadores = repository.findByUsuario_Id(usuarioId, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(jogadores, assembler));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Jogador>> getById(@PathVariable Long id) {
        var jogador = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Jogador","id",id));

        return ResponseEntity.ok(assembler.toModel(jogador));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Jogador>> create(@RequestBody Jogador jogador) {
        repository.save(jogador);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(jogador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Jogador>> update(@PathVariable Long id,
                                                       @RequestBody Jogador updated) {

        return repository.findById(id).map(jogador -> {

            jogador.setUsuario(updated.getUsuario());
            jogador.setMesa(updated.getMesa());
            jogador.setFicha(updated.getFicha());
            jogador.setPapel(updated.getPapel());

            repository.save(jogador);
            return ResponseEntity.ok(assembler.toModel(jogador));

        }).orElseThrow(() -> new RPGNotFoundException("Jogador","id",id));
    }

    @DeleteMapping("/mesas/{mesaId}/jogadores/{usuarioId}/sair")
    public ResponseEntity<Void> sairDaMesa(
            @PathVariable Long mesaId,
            @PathVariable Long usuarioId) {

        var jogador = repository
                .findByMesa_IdAndUsuario_Id(mesaId, usuarioId)
                .orElseThrow(() -> new RPGNotFoundException("Jogador","usuario/mesa",usuarioId));

        repository.delete(jogador);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var jogador = repository.findById(id)
                .orElseThrow(() -> new RPGNotFoundException("Jogador","id",id));

        repository.delete(jogador);
        return ResponseEntity.noContent().build();
    }
}