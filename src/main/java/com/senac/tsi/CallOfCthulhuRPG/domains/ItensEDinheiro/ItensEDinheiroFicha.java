package com.senac.tsi.CallOfCthulhuRPG.domains.ItensEDinheiro;

import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import jakarta.persistence.*;

@Entity
public class ItensEDinheiroFicha {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;
}
