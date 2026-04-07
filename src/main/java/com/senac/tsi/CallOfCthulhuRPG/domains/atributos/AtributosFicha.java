package com.senac.tsi.CallOfCthulhuRPG.domains.atributos;

import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

@Entity
public class AtributosFicha {


    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Ficha é obrigatória")
    @OneToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;

    @Valid
    @Embedded
    private AtributosPrimarios atributosPrimarios;


    @NotNull
    @PositiveOrZero
    private int magia;

    @NotNull
    @PositiveOrZero
    private int vida;

    @NotNull
    @PositiveOrZero
    private int sanidade;

    @NotNull
    @PositiveOrZero
    private int sorte;

    @NotNull
    @PositiveOrZero
    private int corpo;

    @NotNull
    @PositiveOrZero
    private int danoExtra;

    //CONSTRUCTOR

    public AtributosFicha(){}
    public AtributosFicha(AtributosPrimarios atributos,int sorte){
        setAtributosPrimarios(atributos);
        setSorte(sorte);
    }

    //GETTER AND SETTER

    public Long getId() {
        return id;
    }

    public AtributosPrimarios getAtributosPrimarios() {
        return atributosPrimarios;
    }
    public void setAtributosPrimarios(AtributosPrimarios atributos) {
        this.atributosPrimarios = atributos;
        calcularFicha();
    }

    public int getSorte() {
        return sorte;
    }
    public void setSorte(int sorte) { this.sorte = sorte;}

    public int getMagia() {
        return magia;
    }
    public int getSanidade() {
        return sanidade;
    }
    public int getVida() {
        return vida;
    }
    public int getCorpo() {
        return corpo;
    }
    public int getDanoExtra() {
        return danoExtra;
    }

    //METODOS

    public void calcularFicha(){
        if (atributosPrimarios == null) throw  new RuntimeException("Não foi possivel calcular ficha: Atributos Primarios não foram definidos");
        calcularMagia();
        calcularSanidade();
        calcularVida();
        calcularCorpoEDanoExtra();
    }

    public void calcularMagia() {
        this.magia = atributosPrimarios.getPoder().getQuinto();
    }
    public void calcularSanidade() {
        this.sanidade = atributosPrimarios.getPoder().getValor();
    }
    public void calcularVida() {
        this.vida = (atributosPrimarios.getTamanho().getValor() +
                atributosPrimarios.getConstituicao().getValor()) / 10 ;
    }
    public void calcularCorpoEDanoExtra() {
        int valorBase = atributosPrimarios.getForca().getValor() + atributosPrimarios.getTamanho().getValor();
        if(valorBase >= 2 && valorBase <= 64){
            this.danoExtra = -2;
            this.corpo = -2;
        } else if (valorBase <= 84) {
            this.danoExtra = -1;
            this.corpo = -1;
        } else if (valorBase <= 124) {
            this.danoExtra = 0;
            this.corpo = 0;
        }else if (valorBase <= 164) {
            this.danoExtra = 1;//1d4 adicionar valor de dados depois
            this.corpo = 2;
        }else if (valorBase <= 204){
            this.danoExtra = 1; //1d6 adicionar valor de dados depois
            this.corpo = 2;
        }else{
            throw new RuntimeException("Não foi possivel calcular dano Extra e corpo"); //Pensar na mesagem depois
        }
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AtributosFicha that = (AtributosFicha) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString(){
        return """
                Atributos{
                    Id: %s;
                    Primarios: %s;
                    Sorte: %s;
                    Magia: %s;
                    Sanidade: %s;
                    Vida: %s;
                    Corpo %s;
                    Dano Extra: %s
                }
                """.formatted(id,atributosPrimarios != null ? atributosPrimarios.toString() : "null",
                sorte,magia,sanidade,vida,corpo,danoExtra);
    }
}
