package com.senac.tsi.FichasRPG.assemblers;

import com.senac.tsi.FichasRPG.controllers.TagsController;
import com.senac.tsi.FichasRPG.domains.mesa.Jogador;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JogadorAssembler implements RepresentationModelAssembler<Jogador, EntityModel<Jogador>> {
    @Override
    public EntityModel<Jogador> toModel(Jogador jogador){
        return EntityModel.of(jogador,
                linkTo(methodOn(TagsController.class).getTagById(jogador.getId())).withSelfRel()
        );
    }
}
