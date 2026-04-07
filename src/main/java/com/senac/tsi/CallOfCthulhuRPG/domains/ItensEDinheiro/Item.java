package com.senac.tsi.CallOfCthulhuRPG.domains.ItensEDinheiro;

import jakarta.persistence.Embeddable;

import javax.swing.plaf.PanelUI;
import java.util.Objects;

@Embeddable
public class Item {
    private String nome;
    private float preco;

    //CONSTRUCTORs

    public Item(){}
    public Item(String nome,float preco){
     setNome(nome);
     setPreco(preco);
    }

    //GETTERs e SETTERs
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return preco;
    }
    public void setPreco(Float preco) {
        this.preco = preco;
    }

    //METODOS;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Float.compare(preco, item.preco) == 0 &&
                Objects.equals(nome, item.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, preco);
    }

    @Override
    public String toString() {
        return """
                Item{
                    nome='%s',
                    preco=%f
                }""".formatted(nome, preco);
    }
}
