package com.senac.tsi.CallOfCthulhuRPG.domains.ficha;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Embeddable
public class CompanheiroCampanha {

    @NotBlank
    @Size(min = 1,max = 255,message = "numero de caracteres invalidos")
    private String nome;

    @NotBlank
    @Size(min = 1,max = 255,message = "numero de caracteres invalidos")
    private String personagem;

    //CONSTRUCTORs
    public CompanheiroCampanha(){}
    public CompanheiroCampanha(String nome,String personagem) {
        setNome(nome);
        setPersonagem(personagem);
    }

    //GETTERs e SETTERs
    public String getPersonagem() {
        return personagem;
    }
    public void setPersonagem(String personagem) {
        this.personagem = personagem;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    //METODOS

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompanheiroCampanha that = (CompanheiroCampanha) o;
        return Objects.equals(nome, that.nome) &&
                Objects.equals(personagem, that.personagem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, personagem);
    }

    @Override
    public String toString() {
        return """
                CompanheiroCampanha{
                    Nome= %s,
                    Personagem= %s
                }""".formatted(nome,personagem);
    }
}
