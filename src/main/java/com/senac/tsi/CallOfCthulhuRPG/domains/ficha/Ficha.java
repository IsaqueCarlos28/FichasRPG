package com.senac.tsi.CallOfCthulhuRPG.domains.ficha;

import com.senac.tsi.CallOfCthulhuRPG.domains.antecedentes.Antecedentes;
import com.senac.tsi.CallOfCthulhuRPG.domains.companheiros.CompanheirosCampanha;
import com.senac.tsi.CallOfCthulhuRPG.domains.ItensEDinheiro.ItensEDinheiroFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.atributos.AtributosFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.personagens.Investigador;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Ficha {
    @Id
    @GeneratedValue
    private Long id;

    private String nomeJogador;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private Investigador investigador;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private AtributosFicha atributos;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private StatusFicha status;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private HabilidadesFicha habilidades;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private Antecedentes historico;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private ItensEDinheiroFicha itensEDinheiro;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private CompanheirosCampanha companheiros;

    //CONSTRUCTORS
    public Ficha(){}

    public Ficha(String nomeJogador, Investigador investigador, AtributosFicha atributos, StatusFicha status,
                 HabilidadesFicha habilidades, Antecedentes historico, ItensEDinheiroFicha itensEDinheiro,
                 CompanheirosCampanha companheiros) {
        setNomeJogador(nomeJogador);
        setInvestigador(investigador);
        setAtributos(atributos);
        setStatus(status);
        setHabilidades(habilidades);
        setHistorico(historico);
        setItensEDinheiro(itensEDinheiro);
        setCompanheiros(companheiros);
    }

    //GETTER AND SETTER

    public Long getId() {
        return id;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }
    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public Investigador getInvestigador() {
        return investigador;
    }
    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    public AtributosFicha getAtributos() {
        return atributos;
    }
    public void setAtributos(AtributosFicha atributos) {
        this.atributos = atributos;
    }

    public StatusFicha getStatus() {
        return status;
    }
    public void setStatus(StatusFicha status) {
        this.status = status;
    }

    public HabilidadesFicha getHabilidades() {
        return habilidades;
    }
    public void setHabilidades(HabilidadesFicha habilidades) {
        this.habilidades = habilidades;
    }

    public Antecedentes getHistorico() {
        return historico;
    }
    public void setHistorico(Antecedentes historico) {
        this.historico = historico;
    }

    public ItensEDinheiroFicha getItensEDinheiro() {
        return itensEDinheiro;
    }
    public void setItensEDinheiro(ItensEDinheiroFicha itensEDinheiro) {
        this.itensEDinheiro = itensEDinheiro;
    }

    public CompanheirosCampanha getCompanheiros() {
        return companheiros;
    }
    public void setCompanheiros(CompanheirosCampanha companheiros) {
        this.companheiros = companheiros;
    }

    //METODOS


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ficha ficha = (Ficha) o;
        return Objects.equals(id, ficha.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return """
            Ficha{
                id=%d,
                nomeJogador='%s',
                investigador=%s,
                atributos=%s,
                status=%s,
                habilidades=%s,
                historico=%s,
                itensEDinheiro=%s,
                companheiros=%s
            }
            """.formatted(id, nomeJogador, investigador, atributos, status, habilidades, historico,
                itensEDinheiro, companheiros);
    }
}
