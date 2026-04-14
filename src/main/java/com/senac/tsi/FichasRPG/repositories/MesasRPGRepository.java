package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.mesa.Jogador;
import com.senac.tsi.FichasRPG.domains.mesa.MesaRPG;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MesasRPGRepository extends JpaRepository<MesaRPG,Long> {
    Page<Jogador> findByMesa_Id(Long mesaId, Pageable pageable);
    Page<Jogador> findByUsuario_Id(Long usuarioId, Pageable pageable);

    public Page<MesaRPG> findByTags_Id(Long id, Pageable pageable);
    public Page<MesaRPG> findByTags_NomeTag(String nome, Pageable pageable);
}
