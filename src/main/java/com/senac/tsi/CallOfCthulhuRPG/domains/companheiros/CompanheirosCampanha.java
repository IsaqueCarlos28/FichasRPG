package com.senac.tsi.CallOfCthulhuRPG.domains.companheiros;

import com.senac.tsi.CallOfCthulhuRPG.domains.ficha.Ficha;
import jakarta.persistence.*;

@Entity
public class CompanheirosCampanha {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "ficha_id")
    private Ficha ficha;
}
