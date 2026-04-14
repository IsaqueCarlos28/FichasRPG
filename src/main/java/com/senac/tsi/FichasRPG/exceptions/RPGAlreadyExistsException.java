package com.senac.tsi.FichasRPG.exceptions;

public class RPGAlreadyExistsException extends RPGException {
    private final String resource;
    private final String parameter;
    public RPGAlreadyExistsException(String elemento,String parameter) {
        super(elemento +"com esse "+ parameter +" já existe");
        this.resource = elemento;
        this.parameter = parameter;
    }
    public String getResource() {
        return resource;
    }
    public String getParameter(){return parameter;}
}
