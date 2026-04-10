package com.senac.tsi.FichasRPG.domains.atributos;

public enum NomeAtributo {
    FOR("Força"),DES("Destreza"),INT("Inteligência"),
    CON("Constituição"),APA("Aparência"),POD("Poder"),
    TAM("Tamanho"),SAN("Sanidade"),EDU("Educação");

    private String atributo;

    NomeAtributo(String atributo){
        this.atributo = atributo;
    }
}
