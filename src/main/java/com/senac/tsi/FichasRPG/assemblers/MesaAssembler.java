package com.senac.tsi.FichasRPG.assemblers;

import com.senac.tsi.FichasRPG.controllers.TagsController;
import com.senac.tsi.FichasRPG.domains.mesa.MesaRPG;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MesaAssembler implements RepresentationModelAssembler<MesaRPG, EntityModel<MesaRPG>> {
    @Override
    public EntityModel<MesaRPG> toModel(MesaRPG mesa){
        return EntityModel.of(mesa,
                linkTo(methodOn(TagsController.class).getTagById(mesa.getId())).withSelfRel()
        );
    }
}
