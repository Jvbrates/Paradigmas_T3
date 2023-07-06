package entidades;

import infra.entidades.Registro;

public class ativo_financeiro implements Registro {
    private long id;
    private long tipo_ativo_financeiro;
    private String ticker;
    private String nome_empresa;

    @Override
    public String getRotulo() {
        return "Ativo Financeiro";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTipo_ativo_financeiro() {
        return tipo_ativo_financeiro;
    }

    public void setTipo_ativo_financeiro(long tipo_ativo_financeiro) {
        this.tipo_ativo_financeiro = tipo_ativo_financeiro;
    }

    public String getTicket() {
        return (ticker.trim());
    }

    public void setTicket(String ticker) {
        this.ticker = ticker;
    }

    public String getNome_empresa() {
        return nome_empresa;
    }

    public void setNome_empresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }
}
