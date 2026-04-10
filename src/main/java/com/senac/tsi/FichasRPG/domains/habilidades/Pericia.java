package com.senac.tsi.FichasRPG.domains.habilidades;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Embeddable
public class Pericia {
    @NotBlank(message = "Nome da Pericia é obrigatório")
    @Size(min = 1,max = 255,message = "numero de caracteres invalidos")
    private String nome;

    @PositiveOrZero
    @Min(0)
    @Max(100)
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
