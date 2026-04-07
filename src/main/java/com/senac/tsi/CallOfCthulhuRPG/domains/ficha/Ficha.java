package com.senac.tsi.CallOfCthulhuRPG.domains.ficha;

import com.senac.tsi.CallOfCthulhuRPG.domains.antecedentes.Antecedentes;
import com.senac.tsi.CallOfCthulhuRPG.domains.ItensEDinheiro.RecursosFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.atributos.AtributosFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.personagens.Investigador;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

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

    @Embedded
    private StatusPersonagem status;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private HabilidadesFicha habilidades;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private Antecedentes historico;

    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private RecursosFicha itensEDinheiro;

    @ElementCollection
    private Set<CompanheiroCampanha> companheiros;

    //CONSTRUCTORS
    public Ficha(){}

    public Ficha(String nomeJogador, Investigador investigador, AtributosFicha atributos, StatusPersonagem status,
                 HabilidadesFicha habilidades, Antecedentes historico, RecursosFicha itensEDinheiro,
                 Set<CompanheiroCampanha> companheiros) {
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

    public StatusPersonagem getStatus() {
        return status;
    }
    public void setStatus(StatusPersonagem status) {
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

    public RecursosFicha getItensEDinheiro() {
        return itensEDinheiro;
    }
    public void setItensEDinheiro(RecursosFicha itensEDinheiro) {
        this.itensEDinheiro = itensEDinheiro;
    }

    public Set<CompanheiroCampanha> getCompanheiros() {
        return Collections.unmodifiableSet(companheiros);
    }
    public void setCompanheiros(Set<CompanheiroCampanha> companheiros) {
        this.companheiros = companheiros;
    }

    //METODOS

    public void addPessoaImportante(CompanheiroCampanha companheiro) {
        if (companheiro == null) {
            throw new IllegalArgumentException("Pessoa não pode ser null");
        }
        this.companheiros.add(companheiro);
    }
    public void deletePessoaImportante(CompanheiroCampanha companheiro) {
        if (companheiro == null) {
            throw new IllegalArgumentException("Pessoa não pode ser null");
        }
        this.companheiros.remove(companheiro);
    }

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
                nomeJogador= %s,
                investigador= %s,
                atributos= %s,
                status= %s,
                habilidades= %s,
                historico= %s,
                itensEDinheiro= %s,
                companheiros= %s
            }
            """.formatted(id, nomeJogador, investigador, atributos, status, habilidades, historico,
                itensEDinheiro, companheiros);
    }
}
