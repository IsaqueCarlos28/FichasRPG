package com.senac.tsi.FichasRPG.domains.tags;

import com.senac.tsi.FichasRPG.domains.mesa.MesaRPG;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2,max = 30,message = "Numero de caracteres Invalidos")
    @Column(length = 30,nullable = false)
    private String nomeTag;

    @ManyToMany(mappedBy = "tags")
    private List<MesaRPG> mesas = new ArrayList<>();

    //CONSTRUCTORs
    public Tag(){}
    public Tag(String tagName){
        setNomeTag(tagName);
    }

    //GETTER e SETTER


    public Long getId() {
        return id;
    }

    public String getNomeTag() {
        return nomeTag;
    }
    public void setNomeTag(String nomeTag) {
        this.nomeTag = nomeTag;
    }

    public List<MesaRPG> getMesas() {
        return Collections.unmodifiableList(mesas);
    }

    //METODOS

    void addMesaInternal(MesaRPG mesa) {
        this.mesas.add(mesa);
    }

    void removeMesaInternal(MesaRPG mesa) {
        this.mesas.remove(mesa);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return """
                id: %d
                tag: %s
                """.formatted(id, nomeTag);
    }
}
