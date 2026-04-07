package com.senac.tsi.CallOfCthulhuRPG.domains.ficha;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

@Embeddable
public class StatusPersonagem {

    @NotNull
    @PositiveOrZero
    private int vidaAtual;

    @NotNull
    @PositiveOrZero
    private int sanidadeAtual;

    @NotNull
    @PositiveOrZero
    private int sorteAtual;

    @NotNull
    @PositiveOrZero
    private int magiaAtual;

    //CONSTRUCTORS
    public StatusPersonagem(){}
    public StatusPersonagem(int vidaAtual, int sanidadeAtual, int sorteAtual, int magiaAtual){
        setVidaAtual(vidaAtual);
        setSanidadeAtual(sanidadeAtual);
        setSorteAtual(sorteAtual);
        setMagiaAtual(magiaAtual);
    }

    //GETTERs AND SETTERs


    public int getVidaAtual() {
        return vidaAtual;
    }
    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public int getSanidadeAtual() {
        return sanidadeAtual;
    }
    public void setSanidadeAtual(int sanidadeAtual) {
        this.sanidadeAtual = sanidadeAtual;
    }

    public int getSorteAtual() {
        return sorteAtual;
    }
    public void setSorteAtual(int sorteAtual) {
        this.sorteAtual = sorteAtual;
    }

    public int getMagiaAtual() {
        return magiaAtual;
    }
    public void setMagiaAtual(int magiaAtual) {
        this.magiaAtual = magiaAtual;
    }

    //METODOS


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StatusPersonagem that = (StatusPersonagem) o;
        return vidaAtual == that.vidaAtual &&
                sanidadeAtual == that.sanidadeAtual &&
                sorteAtual == that.sorteAtual &&
                magiaAtual == that.magiaAtual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vidaAtual, sanidadeAtual, sorteAtual, magiaAtual);
    }

    @Override
    public String toString() {
        return """
            StatusFicha{
                vidaAtual=%d,
                sanidadeAtual=%d,
                sorteAtual=%d,
                magiaAtual=%d
            }
            """.formatted(vidaAtual, sanidadeAtual, sorteAtual, magiaAtual);
    }
}
