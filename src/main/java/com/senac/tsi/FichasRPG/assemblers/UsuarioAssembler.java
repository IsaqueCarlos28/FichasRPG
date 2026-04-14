package com.senac.tsi.FichasRPG.assemblers;

import com.senac.tsi.FichasRPG.controllers.TagsController;
import com.senac.tsi.FichasRPG.domains.usuario.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(usuario,
                linkTo(methodOn(TagsController.class).getTagById(usuario.getId())).withSelfRel()
        );
    }
}
