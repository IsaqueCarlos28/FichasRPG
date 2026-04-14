package com.senac.tsi.FichasRPG.domains.usuario;

import com.senac.tsi.FichasRPG.domains.modeloFicha.ModeloFicha;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class FichaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @NotBlank
    @Size(min = 1, max = 255, message = "numero de caracteres invalidos")
    private String nomePersonagem;

    @ElementCollection
    @CollectionTable(name = "ficha_usuario_valores",
            joinColumns = @JoinColumn(name = "ficha_usuario_id"))
    @MapKeyColumn(name = "campo_nome")
    @Column(name = "valor")
    private Map<String, String> campos = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "modelo_ficha_id")
    private ModeloFicha modeloFicha;

    //CONSTRUCTOR

    protected FichaUsuario() {}
    public FichaUsuario(String nomePersonagem,ModeloFicha modeloFicha ,Map<String, String> values) {
        setNomePersonagem(nomePersonagem);
        setModeloFicha(modeloFicha);
        setCampos(values);
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setNomePersonagem(String nomePersonagem) {
        this.nomePersonagem = nomePersonagem;
    }
    public String getNomePersonagem() {
        return nomePersonagem;
    }

    public void setCampos(Map<String, String> campos) {
        this.campos = campos;
    }
    public Map<String, String> getCampos() {
        return Collections.unmodifiableMap(campos);
    }

    public ModeloFicha getModeloFicha() {
        return modeloFicha;
    }

    public void setModeloFicha(ModeloFicha modeloFicha) {
        this.modeloFicha = modeloFicha;
    }

    //METODOS

    public void addValue(String campo, String valor) {
        this.campos.put(campo, valor);
    }
    public String getValue(String campo) {
        return this.campos.get(campo);
    }

    // 🔹 Equals & HashCode (JPA safe)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FichaUsuario that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return """
            FichaUsuario{
                id=%d,
                nomePersonagem='%s',
                camposPreenchidos=%d
            }
            """.formatted(id, nomePersonagem, campos.size());
    }
}