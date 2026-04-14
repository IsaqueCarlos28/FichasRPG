package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.mesa.Jogador;
import com.senac.tsi.FichasRPG.domains.mesa.Papel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JogadorRepository extends JpaRepository<Jogador,Long> {
    public Page<Jogador> findByMesa_Id(Long mesa_id, Pageable pageable);
    public Page<Jogador> findByUsuario_Id(Long usuario_id, Pageable pageable);
    public Optional<Jogador> findByMesa_IdAndUsuario_Id(Long mesa_id, Long usuario_id);
    public boolean existsByMesa_IdAndPapel(Long mesa_id, Papel papel);
}
