package com.senac.tsi.FichasRPG.domains.Recursos;

import com.senac.tsi.FichasRPG.domains.ficha.Ficha;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class RecursosFicha {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Ficha é obrigatória")
    @OneToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;


    @NotNull(message = "Nivel de Gasto é obrigatório")
    @Enumerated(EnumType.STRING)
    private NivelGasto nivelDeGasto;

    @NotNull(message = "dinheiro é obrigatorio")
    private float dinheiro = 0;

    @NotNull
    @ElementCollection
    private Set<@NotBlank(message = "Posse Invalida") String> posses = new HashSet<>();

    @NotNull
    @Valid
    @ElementCollection
    private Set<@NotNull Item> inventario = new HashSet<>();

//CONSTRUCTORs

    public RecursosFicha() {}

    public RecursosFicha(Ficha ficha, NivelGasto nivelDeGasto, float dinheiro) {
       setFicha(ficha);
       setNivelDeGasto(nivelDeGasto);
       setDinheiro(dinheiro);
    }

   //GETTERs e SETTERs

    public Ficha getFicha() {
        return ficha;
    }
    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public NivelGasto getNivelDeGasto() {
        return nivelDeGasto;
    }
    public void setNivelDeGasto(NivelGasto nivelDeGasto) {
        this.nivelDeGasto = nivelDeGasto;
    }

    public float getDinheiro() {
        return dinheiro;
    }
    public void setDinheiro(float dinheiro) {
        this.dinheiro = dinheiro;
    }

    public Set<String> getPosses() {
        return Collections.unmodifiableSet(posses);
    }
    public void setPosses(Set<String> posses) {
        this.posses = posses;
    }

    public Set<Item> getInventario() {
        return Collections.unmodifiableSet(inventario);
    }
    public void setInventario(Set<Item> inventario) {
        this.inventario = inventario;
    }


    //METODOS

    public void addPosse(String posse) {
        if (posse == null || posse.isBlank()) {
            throw new IllegalArgumentException("Posse inválida");
        }
        this.posses.add(posse);
    }
    public void removePosse(String posse) {
        if (posse == null || posse.isBlank()) {
            throw new IllegalArgumentException("Posse inválida");
        }
        this.posses.remove(posse);
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser null");
        }
        this.inventario.add(item);
    }
    public void removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser null");
        }
        this.inventario.remove(item);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecursosFicha that = (RecursosFicha) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return """
                ItensEDinheiro{
                    id=%d,
                    nivelDeGasto= %s,
                    dinheiro= %f,
                    posses= %s,
                    inventario= %s
                }
                """.formatted(id,
                nivelDeGasto, dinheiro, posses, inventario);
    }
}
