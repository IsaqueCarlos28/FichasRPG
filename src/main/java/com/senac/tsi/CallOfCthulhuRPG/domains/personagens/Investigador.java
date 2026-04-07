package com.senac.tsi.CallOfCthulhuRPG.domains.personagens;

import com.senac.tsi.CallOfCthulhuRPG.domains.compartilhado.Genero;
import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.*;

@Entity
public class Investigador {
    //ENTITYS
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;

    @NotNull
    @NotBlank(message = "O Nome do Investigador não estar em branco")
    @Size(min = 1,max = 150)
    private String nomeInvestigador;

    @NotNull
    @NotBlank(message = "Ocupação é obrigatorio")
    @Size(min = 1,max = 150)
    private String ocupacao;

    @NotNull
    @NotBlank(message = "A residencia não estar em branco")
    @Size(min = 1,max = 150)
    private String residencia;

    @NotNull
    @NotBlank(message = "Local de nascimento não pode estar em branco")
    @Size(min = 1, max = 150)
    private String localNascimento;

    private Set<String> transtornosMentais;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @NotNull(message = "")
    private int idade;

    //CONSTRUCTORS
    public Investigador(){};
    public Investigador(String nome, String ocupacao, String formacaoAcademica, String localNascimento
            , Set<String> transtornoMentail, Genero genero, int idade){
        setNomeInvestigador(nome);
        setOcupacao(ocupacao);
        setResidencia(formacaoAcademica);
        setLocalNascimento(localNascimento);
        setTranstornosMentais(transtornoMentail);
        setGenero(genero);
        setIdade(idade);
    };

    //GETTER AND SETTER

    public Long getId() {
        return id;
    }

    public String getNomeInvestigador() {
        return nomeInvestigador;
    }
    public void setNomeInvestigador(String nomeInvestigador) {
        this.nomeInvestigador = nomeInvestigador;
    }

    public String getOcupacao() {
        return ocupacao;
    }
    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getResidencia() {
        return residencia;
    }
    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getLocalNascimento() {
        return localNascimento;
    }
    public void setLocalNascimento(String localNascimento) {
        this.localNascimento = localNascimento;
    }

    public Set<String> getTranstornosMentais() {
        return transtornosMentais;
    }
    public void setTranstornosMentais(Set<String> transtornosMentais) {
        this.transtornosMentais = new HashSet<>(transtornosMentais);
    }

    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
/*
    public void addTranstornosMentais(@NotNull String transtornosMentais) {
        boolean added = this.transtornosMentais.add(transtornosMentais);
        if (!added)throw new RuntimeException("O investigador ja possui esse Transtorno Mental");
    }
    public void removeTranstornosMentais(@NotNull String transtornosMentais) {
        boolean removed = this.transtornosMentais.remove(transtornosMentais);
        if(!removed)throw new RuntimeException("Transtorno Mental não existente");
    } */

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Investigador that = (Investigador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString(){
        return """
                Dados do Investigador{
                Id = %s;
                Nome = %s;
                Ocupação = %s;
                Formação = %s;
                Local de Nascimento = %s;
                Transtornos mentais = %s;
                Genero = %s;
                Idade = %s
                }
                """.formatted(id,nomeInvestigador,ocupacao, residencia,
                localNascimento,transtornosMentais.toString(),genero,idade);
    }
}
