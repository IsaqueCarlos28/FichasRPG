package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.historico.HistoricoFicha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoFichaRepositorio extends JpaRepository<HistoricoFicha,Long> {
}
