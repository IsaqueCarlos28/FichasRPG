package com.senac.tsi.CallOfCthulhuRPG.domains.historico;

import com.senac.tsi.CallOfCthulhuRPG.domains.compartilhado.Genero;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Embeddable
public class Pessoa {
    @NotBlank
    @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
    private String nome;

    @NotBlank
    @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
    private int idade;

    @NotNull
    @Enumerated
    private Genero genero;

    @NotBlank
    @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
    private String relacao;

    //CONSTRUCTORS
    public Pessoa(){}
    public Pessoa(String nome, int idade, Genero genero, String relacao) {
        setNome(nome);
        setIdade(idade);
        setGenero(genero);
        setRelacao(relacao);
    }

    //GETTERs e SETTERs
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getRelacao() {
        return relacao;
    }
    public void setRelacao(String relacao) {
        this.relacao = relacao;
    }

    //METODOS

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return idade == pessoa.idade && Objects.equals(nome, pessoa.nome) && genero == pessoa.genero && Objects.equals(relacao, pessoa.relacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, idade, genero, relacao);
    }

    @Override
    public String toString() {
        return """
            Ferimento{ 
                Nome = %s 
                Idade = %s
                Genero = %s
                relação = %s  
            }""".formatted(nome,idade,genero,relacao);
    }
}
