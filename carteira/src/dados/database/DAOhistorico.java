package dados.database;

import entidades.historico;
import infra.dados.dao.database.DAOdatabase;

public class DAOhistorico extends DAOdatabase<historico> {

    public DAOhistorico() {
        super(historico.class);
    }


}
