package com.senac.tsi.FichasRPG.exceptions;

public class RpgAlreadyExistsException extends RuntimeException {
    private final String elemento;
    public RpgAlreadyExistsException(String elemento) {
        super("Elemento já existe");
        this.elemento = elemento;
    }
    public String getElemento() {
        return elemento;
    }
}
