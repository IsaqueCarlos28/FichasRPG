package com.senac.tsi.CallOfCthulhuRPG.domains.ficha;

import com.senac.tsi.CallOfCthulhuRPG.domains.historicoFicha.HistoricoFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.Recursos.RecursosFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.atributos.AtributosFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.CallOfCthulhuRPG.domains.personagens.Investigador;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class Ficha {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 1,max = 255,message = "numero de caracteres invalidos")
    private String nomeJogador;

    @NotNull
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private Investigador investigador;

    @NotNull
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private AtributosFicha atributos;

    @NotNull
    @Embedded
    private StatusPersonagem status;

    @NotNull
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private HabilidadesFicha habilidades;

    @NotNull
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private HistoricoFicha historicoFicha;

    @NotNull
    @OneToOne(mappedBy = "ficha", cascade = CascadeType.ALL)
    private RecursosFicha itensEDinheiro;

    @NotNull
    @Valid
    @ElementCollection
    private Set<@NotNull CompanheiroCampanha> companheiros;

    //CONSTRUCTORS
    public Ficha(){}

    public Ficha(String nomeJogador, Investigador investigador, AtributosFicha atributos, StatusPersonagem status,
                 HabilidadesFicha habilidades, HistoricoFicha historicoFicha, RecursosFicha itensEDinheiro,
                 Set<CompanheiroCampanha> companheiros) {
        setNomeJogador(nomeJogador);
        setInvestigador(investigador);
        setAtributos(atributos);
        setStatus(status);
        setHabilidades(habilidades);
        setHistorico(historicoFicha);
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

    public HistoricoFicha getHistorico() {
        return historicoFicha;
    }
    public void setHistorico(HistoricoFicha historicoFicha) {
        this.historicoFicha = historicoFicha;
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
                historicoFicha= %s,
                itensEDinheiro= %s,
                companheiros= %s
            }
            """.formatted(id, nomeJogador, investigador, atributos, status, habilidades, historicoFicha,
                itensEDinheiro, companheiros);
    }
}
