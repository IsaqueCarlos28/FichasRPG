package com.senac.tsi.FichasRPG.assemblers;

import com.senac.tsi.FichasRPG.controllers.ModeloFichaController;
import com.senac.tsi.FichasRPG.controllers.TagsController;
import com.senac.tsi.FichasRPG.domains.modeloFicha.ModeloFicha;
import com.senac.tsi.FichasRPG.domains.tags.Tag;
import org.springframework.boot.Banner;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ModeloFichaAssembler implements RepresentationModelAssembler<ModeloFicha, EntityModel<ModeloFicha>> {
    @Override
    public EntityModel<ModeloFicha> toModel(ModeloFicha modelo){
        return EntityModel.of(modelo,
                linkTo(methodOn(ModeloFichaController.class).getById(modelo.getId())).withSelfRel()
        );
    }
}
