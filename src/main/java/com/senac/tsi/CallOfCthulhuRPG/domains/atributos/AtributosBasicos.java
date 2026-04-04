package com.senac.tsi.CallOfCthulhuRPG.domains.atributos;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.util.Objects;

@Embeddable
public class AtributosBasicos {

    @Embedded private Atributo forca;
    @Embedded private Atributo constituicao;
    @Embedded private Atributo tamanho;
    @Embedded private Atributo destreza;
    @Embedded private Atributo aparencia;
    @Embedded private Atributo sanidade;
    @Embedded private Atributo inteligencia;
    @Embedded private Atributo poder;
    @Embedded
    private Atributo educacao;

    //CONSTRUCTOR
    public AtributosBasicos(){}

    public AtributosBasicos(int forca, int constituicao, int tamanho,
                            int destreza, int aparencia, int sanidade,
                            int inteligencia, int poder, int educacao) {
        setForca(forca);
        setConstituicao(constituicao);
        setTamanho(tamanho);
        setDestreza(destreza);
        setAparencia(aparencia);
        setSanidade(sanidade);
        setInteligencia(inteligencia);
        setPoder(poder);
        setEducacao(educacao);
    }

    //GETTER AND SETTER

    public Atributo getForca() {
        return forca;
    }
    public void setForca(int valorAtributo) {
        forca.setValor(valorAtributo);
    }

    public Atributo getConstituicao() {
        return constituicao;
    }
    public void setConstituicao(int valorAtributo) {constituicao.setValor(valorAtributo);}

    public Atributo getTamanho() {
        return tamanho;
    }
    public void setTamanho(int valorAtributo) {
        tamanho.setValor(valorAtributo);
    }

    public Atributo getDestreza() {
        return destreza;
    }
    public void setDestreza(int valorAtributo) {destreza.setValor(valorAtributo);}

    public Atributo getAparencia() {
        return aparencia;
    }
    public void setAparencia(int valorAtributo) {aparencia.setValor(valorAtributo);}

    public Atributo getSanidade() {
        return sanidade;
    }
    public void setSanidade(int valorAtributo) {
        sanidade.setValor(valorAtributo);
    }

    public Atributo getInteligencia() {
        return inteligencia;
    }
    public void setInteligencia(int valorAtributo) {
        inteligencia.setValor(valorAtributo);
    }

    public Atributo getPoder() {
        return poder;
    }
    public void setPoder(int valorAtributo) {
        poder.setValor(valorAtributo);
    }

    public Atributo getEducacao() {
        return educacao;
    }
    public void setEducacao(int valorAtributo) {educacao.setValor(valorAtributo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AtributosBasicos that = (AtributosBasicos) o;

        return Objects.equals(forca, that.forca) &&
                Objects.equals(destreza, that.destreza) &&
                Objects.equals(constituicao, that.constituicao) &&
                Objects.equals(tamanho, that.tamanho) &&
                Objects.equals(aparencia, that.aparencia) &&
                Objects.equals(sanidade, that.sanidade) &&
                Objects.equals(inteligencia, that.inteligencia) &&
                Objects.equals(poder, that.poder) &&
                Objects.equals(educacao, that.educacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forca,constituicao,tamanho,destreza,
                aparencia,sanidade,inteligencia,poder,educacao);
    }

    @Override
    public String toString(){
        return """
                AtributosBasicos{
                    Força: %s;
                    Constituicao: %s;
                    Tamanho: %s;
                    Destreza: %s;
                    Aparencia: %s;
                    Sanidade: %s;
                    Inligencia: %s;
                    Poder: %s;
                    Educação: %s;
                }
                """.formatted(forca.toString(),constituicao.toString(),tamanho.toString(),
                destreza.toString(),aparencia.toString(),sanidade.toString(),inteligencia.toString(),
                poder.toString(),educacao.toString());
    }
}
