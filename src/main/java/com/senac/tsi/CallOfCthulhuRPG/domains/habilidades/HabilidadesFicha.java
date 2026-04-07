package com.senac.tsi.CallOfCthulhuRPG.domains.habilidades;

import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Set;

@Entity
public class HabilidadesFicha {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Ficha é obrigatória")
    @OneToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;

    @Valid
    @ElementCollection
    private Set< @NotNull(message = "Pericia do investigador nao pode ser Null") Pericia> PericiasInvestigador;

    @Valid
    @ElementCollection
    private Set<@NotNull(message = "Pericia Ocupacional nao pode ser Null") Pericia> PericiasOcupacional;

    @Valid
    @ElementCollection
    private Set<@NotNull(message = "Arma nao pode ser Null") Arma> Armas;

    //CONSTRUCTOR
    public HabilidadesFicha(){}

    public HabilidadesFicha(Set<Pericia> periciasInvestigador, Set<Pericia> periciasOcupacional, Set<Arma> armas) {
        PericiasInvestigador = periciasInvestigador;
        PericiasOcupacional = periciasOcupacional;
        Armas = armas;
    }

    //GETTERs and SETTERs
    public Long getId() {
        return id;
    }

    public Set<Pericia> getPericiasInvestigador() {
        return PericiasInvestigador;
    }
    public void setPericiasInvestigador(Set<Pericia> periciasInvestigador) {
        PericiasInvestigador = periciasInvestigador;
    }

    public Set<Pericia> getPericiasOcupacional() {
        return PericiasOcupacional;
    }
    public void setPericiasOcupacional(Set<Pericia> periciasOcupacional) {
        PericiasOcupacional = periciasOcupacional;
    }

    public Set<Arma> getArmas() {
        return Armas;
    }
    public void setArmas(Set<Arma> armas) {
        Armas = armas;
    }

    //METODOS

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HabilidadesFicha that = (HabilidadesFicha) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return """
            HabilidadesFicha{
                id=%d,
                PericiasInvestigador=%s,
                PericiasOcupacional=%s,
                Armas=%s
            }
            """.formatted(id, PericiasInvestigador, PericiasOcupacional, Armas);
    }
}
