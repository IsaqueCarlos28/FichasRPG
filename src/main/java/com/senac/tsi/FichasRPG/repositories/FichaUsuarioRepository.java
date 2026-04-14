package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.usuario.FichaUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaUsuarioRepository extends JpaRepository<FichaUsuario,Long> {
    Page<FichaUsuario> findByUsuario_Id(Long usuarioId, Pageable pageable);
    Page<FichaUsuario> findByModeloFicha_Id(Long modeloId, Pageable pageable);
}
