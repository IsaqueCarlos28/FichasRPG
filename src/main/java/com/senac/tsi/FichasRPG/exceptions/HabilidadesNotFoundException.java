package com.senac.tsi.FichasRPG.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class HabilidadesNotFoundException extends ChangeSetPersister.NotFoundException {
    public HabilidadesNotFoundException(String message) {
        super();
    }
}
