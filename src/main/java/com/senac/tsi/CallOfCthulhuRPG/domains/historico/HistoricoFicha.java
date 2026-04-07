package com.senac.tsi.CallOfCthulhuRPG.domains.historico;

import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "antecedentes")
public class HistoricoFicha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ficha é obrigatória")
    @OneToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;

    @NotNull
    @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
    private String descricaoPessoais;

    @NotNull
    @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
    private String ideologias;

    @NotNull
    @ElementCollection
    private Set<@NotBlank @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
            Pessoa> pessoasImportantes = new HashSet<>();

    @NotNull
    @ElementCollection
    private Set<@NotBlank@Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
            String> itensValiosos = new HashSet<>();

    @NotNull
    @ElementCollection
    private Set<@NotBlank @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
            String> tracos = new HashSet<>();

    @NotNull
    @ElementCollection
    private Set<@NotBlank @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
            String> tomosArcanos = new HashSet<>();

    @NotNull
    @ElementCollection
    private Set<@NotBlank @Size(min = 2, max = 255, message = "Quantidade de caracteres invalidos")
            String> encontrosEstranhos = new HashSet<>();

    @NotNull
    @Valid
    @ElementCollection
    private Set<Ferimento> machucadosECicatrizes = new HashSet<>();

    @NotNull
    @ElementCollection
    private Set<String> fobiasEManias = new HashSet<>();

    //CONTRUCTORs
    public HistoricoFicha(){}
    public HistoricoFicha(Ficha ficha, String descricaoPessoais, String ideologias, Set<Pessoa> pessoasImportantes,
                          Set<String> itensValiosos, Set<String> tracos, Set<String> tomosArcanos, Set<String> encontrosEstranhos,
                          Set<Ferimento> machucadosECicatrizes, Set<String> fobiasEManias) {
        setFicha(ficha);
        setDescricaoPessoais(descricaoPessoais);
        setIdeologias(ideologias);
        setPessoasImportantes(pessoasImportantes);
        setItensValiosos(itensValiosos);
        setTracos(tracos);
        setTomosArcanos(tomosArcanos);
        setEncontrosEstranhos(encontrosEstranhos);
        setMachucadosECicatrizes(machucadosECicatrizes);
        setFobiasEManias(fobiasEManias);
    }
    // GETTERs E SETTERs

    public Long getId() {
        return id;
    }

    public Ficha getFicha() {
        return ficha;
    }
    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public String getDescricaoPessoais() {
        return descricaoPessoais;
    }
    public void setDescricaoPessoais(String descricaoPessoais) {
        this.descricaoPessoais = descricaoPessoais;
    }

    public String getIdeologias() {
        return ideologias;
    }
    public void setIdeologias(String ideologias) {
        this.ideologias = ideologias;
    }

    public Set<Pessoa> getPessoasImportantes() {
        return Collections.unmodifiableSet(pessoasImportantes);
    }
    public void setPessoasImportantes(Set<Pessoa> pessoasImportantes) {
        this.pessoasImportantes = pessoasImportantes;
    }

    public Set<String> getItensValiosos() {
        return Collections.unmodifiableSet(itensValiosos);
    }
    public void setItensValiosos(Set<String> itensValiosos) {
        this.itensValiosos = itensValiosos;
    }

    public Set<String> getTracos() {
        return Collections.unmodifiableSet(tracos);
    }
    public void setTracos(Set<String> tracos) {
        this.tracos = tracos;
    }

    public Set<String> getTomosArcanos() {
        return Collections.unmodifiableSet(tomosArcanos);
    }
    public void setTomosArcanos(Set<String> tomosArcanos) {
        this.tomosArcanos = tomosArcanos;
    }

    public Set<String> getEncontrosEstranhos() {
        return Collections.unmodifiableSet(encontrosEstranhos);
    }
    public void setEncontrosEstranhos(Set<String> encontrosEstranhos) {
        this.encontrosEstranhos = encontrosEstranhos;
    }

    public Set<Ferimento> getMachucadosECicatrizes() {
        return Collections.unmodifiableSet(machucadosECicatrizes);
    }
    public void setMachucadosECicatrizes(Set<Ferimento> machucadosECicatrizes) {
        this.machucadosECicatrizes = machucadosECicatrizes;
    }

    public Set<String> getFobiasEManias() {
        return Collections.unmodifiableSet(fobiasEManias);
    }
    public void setFobiasEManias(Set<String> fobiasEManias) {
        this.fobiasEManias = fobiasEManias;
    }

    //METODOS

    public void addItemValioso(String item) {
        if (item == null || item.isBlank()) {
            throw new IllegalArgumentException("Item valioso inválido");
        }
        this.itensValiosos.add(item);
    }
    public void removeItemValioso(String item) {
        if (item == null || item.isBlank()) {
            throw new IllegalArgumentException("Item valioso inválido");
        }
        this.itensValiosos.remove(item);
    }

    public void addTraco(String traco) {
        if (traco == null || traco.isBlank()) {
            throw new IllegalArgumentException("Traço inválido");
        }
        this.tracos.add(traco);
    }
    public void removeTraco(String traco) {
        if (traco == null || traco.isBlank()) {
            throw new IllegalArgumentException("Traço inválido");
        }
        this.tracos.remove(traco);
    }

    public void addTomoArcano(String tomo) {
        if (tomo == null || tomo.isBlank()) {
            throw new IllegalArgumentException("Tomo arcano inválido");
        }
        this.tomosArcanos.add(tomo);
    }
    public void removeTomoArcano(String tomo) {
        if (tomo == null || tomo.isBlank()) {
            throw new IllegalArgumentException("Tomo arcano inválido");
        }
        this.tomosArcanos.remove(tomo);
    }

    public void addFobiaEMania(String fobiaEMania) {
        if (fobiaEMania == null || fobiaEMania.isBlank()) {
            throw new IllegalArgumentException("Fobia/Mania não pode ser null");
        }
        this.fobiasEManias.add(fobiaEMania);
    }
    public void removeFobiaEMania(String fobiaEMania) {
        if (fobiaEMania == null || fobiaEMania.isBlank()) {
            throw new IllegalArgumentException("Fobia/Mania não pode ser null");
        }
        this.fobiasEManias.remove(fobiaEMania);
    }

    public void addEncontroEstranho(String encontro) {
        if (encontro == null || encontro.isBlank()) {
            throw new IllegalArgumentException("Encontro estranho inválido");
        }
        this.encontrosEstranhos.add(encontro);
    }
    public void removeEncontroEstranho(String encontro) {
        if (encontro == null || encontro.isBlank()) {
            throw new IllegalArgumentException("Encontro estranho inválido");
        }
        this.encontrosEstranhos.remove(encontro);
    }

    public void addFerimento(Ferimento ferimento) {
        if (ferimento == null) {
            throw new IllegalArgumentException("Ferimento não pode ser null");
        }
        this.machucadosECicatrizes.add(ferimento);
    }
    public void removeFerimento(Ferimento ferimento) {
        if (ferimento == null) {
            throw new IllegalArgumentException("Ferimento não pode ser null");
        }
        this.machucadosECicatrizes.remove(ferimento);
    }

    public void addPessoaImportante(Pessoa pessoa) {
        if (pessoa == null) {
            throw new IllegalArgumentException("Pessoa não pode ser null");
        }
        this.pessoasImportantes.add(pessoa);
    }
    public void deletePessoaImportante(Pessoa pessoa) {
        if (pessoa == null) {
            throw new IllegalArgumentException("Pessoa não pode ser null");
        }
        this.pessoasImportantes.remove(pessoa);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HistoricoFicha that = (HistoricoFicha) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return """
                Antecedentes{
                    id=%d,
                    descricaoPessoais='%s',
                    ideologias='%s',
                    pessoasImportantes=%s,
                    itensValiosos=%s,
                    tracos=%s,
                    machucadosECicatrizes=%s,
                    fobiasEManias=%s,
                    tomosArcanos=%s,
                    encontrosEstranhos=%s
                }
                """.formatted(id, descricaoPessoais, ideologias, pessoasImportantes, itensValiosos,
                tracos, machucadosECicatrizes, fobiasEManias, tomosArcanos, encontrosEstranhos);
    }
}