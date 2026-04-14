package com.senac.tsi.FichasRPG.domains.modeloFicha;

import com.senac.tsi.FichasRPG.exceptions.RPGAlreadyExistsException;
import com.senac.tsi.FichasRPG.exceptions.RPGNotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class ModeloFicha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String sistemaRPG;

    @ElementCollection
    @CollectionTable(name = "modelo_ficha_campos",
            joinColumns = @JoinColumn(name = "modelo_ficha_id"))
    private List<CampoFicha> fields = new ArrayList<>();



    //CONSTRUCTORs
    protected ModeloFicha() {}
    public ModeloFicha(String sistemaRPG, List<CampoFicha> fields) {
        setSistemaRPG(sistemaRPG);
        setFields(fields);
    }

    public Long getId() {
        return id;
    }

    public void setSistemaRPG(String sistemaRPG) {
        this.sistemaRPG = sistemaRPG;
    }
    public String getSistemaRPG() {
        return sistemaRPG;
    }

    public void setFields(List<CampoFicha> fields) {
        this.fields = fields;
    }
    public List<CampoFicha> getFields() {
        return Collections.unmodifiableList(fields);
    }

    //METODOS
    public void addField(CampoFicha campo) {
        boolean exists = fields.stream()
                .anyMatch(f -> f.getNome().equals(campo.getNome()));
        if (exists) {
            throw new RPGAlreadyExistsException("Nome", campo.getNome());
        }

        this.fields.add(campo);
    }
    public void deleteField(CampoFicha campo) {
        boolean exists = fields.stream()
                .anyMatch(f -> f.getNome().equals(campo.getNome()));
        if (!exists) {
            throw new RPGNotFoundException("Campo","nome",campo.getNome());
        }

        this.fields.remove(campo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModeloFicha that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return """
            ModeloFicha{
                id=%d,
                sistemaRPG=%s,
                fields=%d
            }
            """.formatted(id, sistemaRPG, fields.size());
    }
}