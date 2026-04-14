package com.senac.tsi.FichasRPG.assemblers;


import com.senac.tsi.FichasRPG.controllers.FichaUsuarioController;
import com.senac.tsi.FichasRPG.domains.usuario.FichaUsuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FichaUsuarioAssembler implements RepresentationModelAssembler<FichaUsuario,EntityModel<FichaUsuario>> {

    @Override
    public EntityModel<FichaUsuario> toModel(FichaUsuario ficha){
        return EntityModel.of(ficha,
                linkTo(methodOn(FichaUsuarioController.class).getById(ficha.getId())).withSelfRel()
        );
    }
}
