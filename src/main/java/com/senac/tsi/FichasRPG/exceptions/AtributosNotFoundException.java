package com.senac.tsi.FichasRPG.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class AtributosNotFoundException extends ChangeSetPersister.NotFoundException {
    public AtributosNotFoundException(String message) {
        super();
    }
}
