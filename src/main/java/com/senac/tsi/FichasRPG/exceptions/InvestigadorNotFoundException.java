package com.senac.tsi.FichasRPG.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class InvestigadorNotFoundException extends ChangeSetPersister.NotFoundException {
    public InvestigadorNotFoundException(String message) {
        super();
    }
}
