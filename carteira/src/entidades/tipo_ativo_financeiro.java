package entidades;

import infra.entidades.Registro;

import java.util.SplittableRandom;

public class tipo_ativo_financeiro implements Registro {
    private long id;

    private String alias;

    @Override
    public String getRotulo() {
        return "Tipo de Ativo Financeiro";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
