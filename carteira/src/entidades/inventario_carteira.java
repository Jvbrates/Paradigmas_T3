package entidades;

import infra.entidades.Registro;

import java.sql.Date;
public class inventario_carteira implements Registro {

    private long id;

    private long id_user;

    private long id_ativo;

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_ativo() {
        return id_ativo;
    }

    public void setId_ativo(long id_ativo) {
        this.id_ativo = id_ativo;
    }





    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    private int quantidade;

    @Override
    public String getRotulo() {
        return null;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
