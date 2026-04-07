package com.senac.tsi.CallOfCthulhuRPG.domains.habilidades;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Pericia {
    private String nome;
    private int valor;

    //CONSTRUCTORs
    public Pericia(){}

    public Pericia(String nome, int valor) {
        setNome(nome);
        setValor(valor);
    }

    //GETTERs e SETTERs
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }

    //METODOS

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pericia pericia = (Pericia) o;
        return valor == pericia.valor && Objects.equals(nome, pericia.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, valor);
    }

    @Override
    public String toString() {
        return """
            Pericia{
                nome='%s',
                valor=%d
            }
            """.formatted(nome, valor);
    }
}
