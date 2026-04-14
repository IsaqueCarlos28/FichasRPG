package com.senac.tsi.FichasRPG.domains.modeloFicha;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable
public class CampoFicha {

    @NotBlank
    private String nome; // "strength"

    // JPA requires a no-args constructor
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCampo tipo; // "NUMBER", "TEXT"

    protected CampoFicha() {}

    public CampoFicha(String name, TipoCampo tipo) {
        setNome(name);
        setType(tipo);
    }

    //GETTER e SETTER

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCampo getType() {
        return tipo;
    }
    public void setType(TipoCampo tipo) {
        this.tipo = tipo;
    }

    // Equals and HashCode are IMPORTANT for @ElementCollection
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CampoFicha that)) return false;
        return Objects.equals(nome, that.nome) &&
                tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, tipo);
    }

    @Override
    public String toString() {
        return """
            ModeloFicha{
                nome: %s,
                tipo: %s
            }
            """.formatted(nome,tipo);
    }
}