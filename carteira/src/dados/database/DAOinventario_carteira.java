package dados.database;

import entidades.inventario_carteira;
import infra.dados.dao.database.DAOdatabase;

public class DAOinventario_carteira extends DAOdatabase<inventario_carteira> {

    public DAOinventario_carteira(){
        super(inventario_carteira.class);
    }
}
