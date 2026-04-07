package com.senac.tsi.CallOfCthulhuRPG.domains.historico;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Embeddable
public class Ferimento {
    @NotBlank
    @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
    private String tipo;

    @NotBlank
    @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
    private String local;

    //CONSTRUCTORs
    public Ferimento(){}
    public Ferimento(String tipo,String local){
        setTipo(tipo);
        setLocal(local);
    }

    //GETTERs and SETTERs
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //METODOS
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ferimento ferimento = (Ferimento) o;
        return Objects.equals(tipo, ferimento.tipo) &&
                Objects.equals(local, ferimento.local);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, local);
    }

    @Override
    public String toString() {
        return """
            Ferimento{ 
                tipo= %s 
                local= %s  
            }""".formatted(tipo,local);
    }
}
