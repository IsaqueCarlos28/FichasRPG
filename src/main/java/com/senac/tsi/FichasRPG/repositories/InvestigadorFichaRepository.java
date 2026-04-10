package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.personagens.Investigador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestigadorFichaRepository extends JpaRepository<Investigador,Long> {
}
