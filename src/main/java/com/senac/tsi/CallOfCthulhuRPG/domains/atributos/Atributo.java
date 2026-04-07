package com.senac.tsi.CallOfCthulhuRPG.domains.atributos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

@Embeddable
public class Atributo {

    @NotNull
    @Min(0)
    @Max(100)
    private int valor;

    //CONSTRUCTORS

    public Atributo(){}
    public Atributo(int valor){
        setValor(valor);
    }

    //GETTER AND SETTER

    public void setValor(int valor) {
        this.valor = valor;
    }
    public int getValor() {
        return valor;
    }

    //METODOS

    public int getMetade() {

        return valor / 2;
    }
    public int getQuinto() {
        return valor / 5;
    }

    public void addAtributo(int valorAdicionado){
        int novoValor = this.valor + valorAdicionado;

        if (novoValor > 100) {
            throw new RuntimeException("Valor do atributo não pode ser maior que 100");
        }

        this.valor = novoValor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Atributo atributo = (Atributo) o;
        return valor == atributo.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valor);
    }

    @Override
    public String toString() {
        return """
                Atributo{ +
                Valor = %s;
                Metade = %s;
                Quinto = %s;
                """.formatted(valor,getMetade(),getQuinto());
    }
}
