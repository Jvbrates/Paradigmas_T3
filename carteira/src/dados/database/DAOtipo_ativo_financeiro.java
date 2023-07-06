package dados.database;

import entidades.tipo_ativo_financeiro;
import infra.dados.dao.database.DAOdatabase;

public class DAOtipo_ativo_financeiro extends DAOdatabase<tipo_ativo_financeiro> {
    public DAOtipo_ativo_financeiro(){
        super(tipo_ativo_financeiro.class);
    }
}
