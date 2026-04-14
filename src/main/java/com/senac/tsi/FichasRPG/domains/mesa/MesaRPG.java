package com.senac.tsi.FichasRPG.domains.mesa;

import com.senac.tsi.FichasRPG.domains.tags.Tag;
import com.senac.tsi.FichasRPG.domains.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class MesaRPG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100, message = "nome invalido")
    private String nome;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jogador> jogadores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "mesa_rpg_tags",
            joinColumns = @JoinColumn(name = "mesa_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"mesa_id", "tag_id"})
    )
    private List<Tag> tags = new ArrayList<>();

    //CONSTRUCTORs
    protected MesaRPG() {}
    public MesaRPG(String nome, List<Jogador> jogadores) {
        setNome(nome);
        setJogadores(jogadores);
    }

    //GETTERs e SETTERs
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setJogadores(List<Jogador> jogadores){
        this.jogadores = jogadores;
    }
    public List<Jogador> getJogadores() {
        return Collections.unmodifiableList(jogadores);
    }

    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }

    //METODOS

    public void addJogador(Jogador jogador) {
        jogadores.add(jogador);
        jogador.setMesa(this);
    }

    public void removeJogador(Jogador jogador) {
        jogadores.remove(jogador);
        jogador.setMesa(null);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getMesas().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getMesas().remove(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MesaRPG that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return """
            Mesa{
                id=%d,
                nome='%s',
                jogadores=%d
            }
            """.formatted(id, nome, jogadores.size());
    }
}