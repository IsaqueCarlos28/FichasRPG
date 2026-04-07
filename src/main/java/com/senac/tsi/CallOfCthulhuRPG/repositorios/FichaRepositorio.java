package com.senac.tsi.CallOfCthulhuRPG.repositorios;

import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaRepositorio extends JpaRepository<Ficha,Long> {
}
