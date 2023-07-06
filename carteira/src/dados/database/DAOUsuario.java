package dados.database;


import entidades.usuario;
import infra.dados.dao.database.DAOdatabase;

public class DAOUsuario extends DAOdatabase<usuario> {
    public DAOUsuario() {
        super(usuario.class);
    }
}
