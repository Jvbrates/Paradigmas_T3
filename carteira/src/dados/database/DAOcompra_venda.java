package dados.database;

import entidades.historico;
import infra.dados.dao.database.DAOdatabase;

public class DAOcompra_venda extends DAOdatabase<historico> {

    public DAOcompra_venda() {
        super(historico.class);
    }


}
