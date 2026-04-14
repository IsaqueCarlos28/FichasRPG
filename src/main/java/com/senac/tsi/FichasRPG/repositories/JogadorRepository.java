package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.mesa.Jogador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Jogador,Long> {
    Page<Jogador> findByMesa_Id(Long mesaId, Pageable pageable);
    Page<Jogador> findByUsuario_Id(Long usuarioId, Pageable pageable);
}
