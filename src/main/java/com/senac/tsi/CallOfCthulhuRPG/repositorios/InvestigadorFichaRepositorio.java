package com.senac.tsi.CallOfCthulhuRPG.repositorios;

import com.senac.tsi.CallOfCthulhuRPG.domains.personagens.Investigador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestigadorFichaRepositorio extends JpaRepository<Investigador,Long> {
}
