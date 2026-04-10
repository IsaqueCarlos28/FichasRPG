package com.senac.tsi.FichasRPG.domains.atributos;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable
public class AtributosPrimarios {

    @NotNull
    @Min(0)
    @Max(100)
    private int forca;

    @NotNull @Valid
    @Min(0)
    @Max(100)
    private int constituicao;

    @NotNull
    @Min(0)
    @Max(100)
    private int tamanho;

    @NotNull
    @Min(0)
    @Max(100)
    private int destreza;

    @NotNull
    @Min(0)
    @Max(100)
    private int aparencia;

    @NotNull 
    @Min(0)
    @Max(100)
    private int sanidade;

    @NotNull
    @Min(0)
    @Max(100)
    private int inteligencia;

    @NotNull
    @Min(0)
    @Max(100)
    private int poder;

    @NotNull
    @Min(0)
    @Max(100)
    private int educacao;

    //CONSTRUCTOR
    public AtributosPrimarios(){}

    public AtributosPrimarios(int forca, int constituicao, int tamanho,
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

    public int getForca() {
        return forca;
    }
    public void setForca(int valorAtributo) {forca = valorAtributo;    }

    public int getConstituicao() {
        return constituicao;
    }
    public void setConstituicao(int valorAtributo) {constituicao = valorAtributo;}

    public int getTamanho() {
        return tamanho;
    }
    public void setTamanho(int valorAtributo) {
        tamanho = valorAtributo;
    }

    public int getDestreza() {
        return destreza;
    }
    public void setDestreza(int valorAtributo) {destreza = valorAtributo;;}

    public int getAparencia() {
        return aparencia;
    }
    public void setAparencia(int valorAtributo) {aparencia = valorAtributo;}

    public int getSanidade() {
        return sanidade;
    }
    public void setSanidade(int valorAtributo) {
        sanidade = valorAtributo;
    }

    public int getInteligencia() {
        return inteligencia;
    }
    public void setInteligencia(int valorAtributo) {
        inteligencia = valorAtributo;
    }

    public int getPoder() {
        return poder;
    }
    public void setPoder(int valorAtributo) {
        poder = valorAtributo;
    }

    public int getEducacao() {
        return educacao;
    }
    public void setEducacao(int valorAtributo) {educacao = valorAtributo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AtributosPrimarios that = (AtributosPrimarios) o;

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
                    Força: %d;
                    Constituicao: %d;
                    Tamanho: %d;
                    Destreza: %d;
                    Aparencia: %d;
                    Sanidade: %d;
                    Inligencia: %d;
                    Poder: %d;
                    Educação: %d;
                }
                """.formatted(forca,constituicao,tamanho,destreza,aparencia,sanidade,inteligencia,
                poder,educacao);
    }
}
