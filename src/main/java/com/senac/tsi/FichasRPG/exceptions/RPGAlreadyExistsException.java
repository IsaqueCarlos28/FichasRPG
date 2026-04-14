package com.senac.tsi.FichasRPG.exceptions;

public class RPGAlreadyExistsException extends RPGException {
    private final String resource;
    private final String value;
    public RPGAlreadyExistsException(String elemento,String valor) {
        super(elemento +"com esse "+ valor +" já existe");
        this.resource = elemento;
        this.value = valor;
    }
    public String getResource() {
        return resource;
    }
    public String getValue(){return value;}
}
