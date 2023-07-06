package entidades;

import infra.entidades.Registro;

import java.sql.Date;

public class cotacao implements Registro {
    private long id;

    private long id_ativo;

    public long getId_ativo() {
        return id_ativo;
    }

    public void setId_ativo(long id_ativo) {
        this.id_ativo = id_ativo;
    }

    private java.sql.Date dia;

    private double valor;

    @Override
    public String getRotulo() {
        return "Cotação";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public java.sql.Date getDia() {
        return dia;
    }

    public void setDia(java.sql.Date dia) {
        this.dia = dia;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
