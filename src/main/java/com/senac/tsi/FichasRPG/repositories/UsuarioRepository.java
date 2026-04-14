package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    boolean existsByEmail(String email);
}
