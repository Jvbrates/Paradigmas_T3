package dados.database;

import entidades.cotacao;
import infra.dados.dao.database.DAOdatabase;

public class DAOcotacoes extends DAOdatabase<cotacao> {

    public DAOcotacoes() {
        super(cotacao.class);
    }


}
