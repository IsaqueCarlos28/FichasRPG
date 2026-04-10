package com.senac.tsi.FichasRPG.repositories;

import com.senac.tsi.FichasRPG.domains.ficha.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaRepositorio extends JpaRepository<Ficha,Long> {
}
