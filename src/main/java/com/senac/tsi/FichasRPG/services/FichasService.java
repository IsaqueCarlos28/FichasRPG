package com.senac.tsi.FichasRPG.services;

import com.senac.tsi.FichasRPG.domains.Recursos.RecursosFicha;
import com.senac.tsi.FichasRPG.domains.atributos.AtributosFicha;
import com.senac.tsi.FichasRPG.domains.ficha.Ficha;
import com.senac.tsi.FichasRPG.domains.habilidades.HabilidadesFicha;
import com.senac.tsi.FichasRPG.domains.historico.HistoricoFicha;
import com.senac.tsi.FichasRPG.domains.personagens.Investigador;

public class FichasService {

    public Ficha criarFichaVazia(){
        AtributosFicha atributos = new AtributosFicha();
        HabilidadesFicha habilidades = new HabilidadesFicha();
        HistoricoFicha historico = new HistoricoFicha();
        Investigador investigador = new Investigador();
        RecursosFicha recursos = new RecursosFicha();

        return new Ficha(investigador,atributos,habilidades,historico,recursos);

    }

}
