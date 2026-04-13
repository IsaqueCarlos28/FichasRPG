package com.senac.tsi.FichasRPG.exceptions;

public class RPGNotFoundException extends RPGException {
    private final String resource;
    private final String field;
    private final Object value;

    public RPGNotFoundException(String resource, String field, Object value) {
        super(resource + " not found");
        this.resource = resource;
        this.field = field;
        this.value = value;
    }

    public String getResource() { return resource; }
    public String getField() { return field; }
    public Object getValue() { return value; }
}
