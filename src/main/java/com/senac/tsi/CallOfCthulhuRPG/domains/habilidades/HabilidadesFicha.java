package com.senac.tsi.CallOfCthulhuRPG.domains.habilidades;

import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class HabilidadesFicha {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;

    @ElementCollection
    private Set<Pericia> PericiasInvestigador;

    @ElementCollection
    private Set<Pericia> PericiasOcupacional;

    @ElementCollection
    private Set<Arma> Armas;

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
