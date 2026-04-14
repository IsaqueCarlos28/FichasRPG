package com.senac.tsi.FichasRPG.domains.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100, message = "nome invalido")
    private String nome;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    // 🔗 Relacionamento com fichas
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FichaUsuario> fichas = new ArrayList<>();

    // CONSTRUCTOR
    protected Usuario() {}
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
    }

    //GETTE e SETTER
    public Long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setFichas(List<FichaUsuario> fichas){
        this.fichas = fichas;
    }
    public List<FichaUsuario> getFichas() {
        return Collections.unmodifiableList(fichas);
    }

    //
    public void addFicha(FichaUsuario ficha) {
        fichas.add(ficha);
        ficha.setUsuario(this);
    }

    public void removeFicha(FichaUsuario ficha) {
        fichas.remove(ficha);
        ficha.setUsuario(null);
    }

    // 🔹 Equals & HashCode (JPA safe)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return """
            Usuario{
                id=%d,
                nome='%s',
                email='%s',
                fichas=%d
            }
            """.formatted(id, nome, email, fichas.size());
    }
}