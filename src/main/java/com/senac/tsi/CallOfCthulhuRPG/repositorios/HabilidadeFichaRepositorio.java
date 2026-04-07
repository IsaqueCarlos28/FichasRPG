package com.senac.tsi.CallOfCthulhuRPG.repositorios;

import com.senac.tsi.CallOfCthulhuRPG.domains.habilidades.HabilidadesFicha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabilidadeFichaRepositorio extends JpaRepository<HabilidadesFicha,Long> {
}
