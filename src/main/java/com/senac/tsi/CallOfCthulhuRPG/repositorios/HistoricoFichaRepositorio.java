package com.senac.tsi.CallOfCthulhuRPG.repositorios;

import com.senac.tsi.CallOfCthulhuRPG.domains.historico.HistoricoFicha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoFichaRepositorio extends JpaRepository<HistoricoFicha,Long> {
}
