package com.senac.tsi.FichasRPG.exceptions;

public class RPGValidationException extends RPGException {
    private final String process;
    private final String wrongParameter;
    private final String expectedParameter;
    public RPGValidationException(String processo, String parametroErrado, String parametroEsperado) {
        super("Parametro invalido");
        this.process = processo;
        this.wrongParameter = parametroErrado;
        this.expectedParameter = parametroEsperado;
    }
    public String getProcess(){return this.process;}
    public String getWrongParameter(){return this.wrongParameter;}
    public String getExpectedParameter(){return this.expectedParameter;}
}
