package dados.database;

import entidades.ativo_financeiro;
import infra.dados.dao.database.DAOdatabase;

public class DAOativo_financeiro extends DAOdatabase<ativo_financeiro> {
    DAOativo_financeiro(){
        super(ativo_financeiro.class);

    }
}

