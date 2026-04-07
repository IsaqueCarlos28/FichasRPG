package com.senac.tsi.CallOfCthulhuRPG.domains.habilidades;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Arma {
    private String nome;
    private Pericia pericia;
    private String dano;
    private String alcance;
    private int municao;

    //CONSTRUCTORs
    public Arma(){}
    public Arma(String nome, Pericia pericia, String dano, String alcance, int municao) {
        setNome(nome);
        setPericia(pericia);
        setDano(dano);
        setAlcance(alcance);
        setMunicao(municao);
    }

    //GETTERs e SETTERs
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Pericia getPericia() {
        return pericia;
    }
    public void setPericia(Pericia pericia) {
        this.pericia = pericia;
    }

    public String getDano() {
        return dano;
    }
    public void setDano(String dano) {
        this.dano = dano;
    }

    public String getAlcance() {
        return alcance;
    }
    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public int getMunicao() {
        return municao;
    }
    public void setMunicao(int municao) {
        this.municao = municao;
    }

    //METODOS


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Arma arma = (Arma) o;
        return municao == arma.municao &&
                Objects.equals(nome, arma.nome) &&
                Objects.equals(pericia, arma.pericia) &&
                Objects.equals(dano, arma.dano) &&
                Objects.equals(alcance, arma.alcance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, pericia, dano, alcance, municao);
    }

    @Override
    public String toString() {
        return """
            Arma{
                nome='%s',
                pericia=%s,
                dano='%s',
                alcance='%s',
                municao=%d
            }
            """.formatted(nome, pericia, dano, alcance, municao);
    }
}
