package com.senac.tsi.FichasRPG.domains.mesa;

import com.senac.tsi.FichasRPG.domains.usuario.FichaUsuario;
import com.senac.tsi.FichasRPG.domains.usuario.Usuario;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private MesaRPG mesa;

    @OneToOne
    @JoinColumn(name = "ficha_id")
    private FichaUsuario ficha;

    @Enumerated(EnumType.STRING)
    private Papel papel;


    protected Jogador() {}

    public Jogador(Usuario usuario, MesaRPG mesa, Papel papel) {
        setUsuario(usuario);
        setMesa(mesa);
        setPapel(papel);
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public MesaRPG getMesa() {
        return mesa;
    }

    public FichaUsuario getFicha() {
        return ficha;
    }

    public Papel getPapel() {
        return papel;
    }

    // 🔹 Setters (used in constructor)

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMesa(MesaRPG mesa) {
        this.mesa = mesa;
    }

    public void setFicha(FichaUsuario ficha) {
        this.ficha = ficha;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    // 🔹 Equals & HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jogador that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return """
            Jogador{
                id=%d,
                usuario=%s,
                papel=%s
            }
            """.formatted(
                id,
                usuario != null ? usuario.getId() : null,
                papel
        );
    }
}