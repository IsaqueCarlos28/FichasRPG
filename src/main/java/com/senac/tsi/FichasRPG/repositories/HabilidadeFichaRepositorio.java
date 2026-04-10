package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.habilidades.HabilidadesFicha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabilidadeFichaRepositorio extends JpaRepository<HabilidadesFicha,Long> {
}
