package entidades;
import infra.entidades.Registro;
import java.sql.Date;


public class historico implements Registro {

    private long id;

    private long id_user;

    private long id_ativo;

    @Override
    public String getRotulo() {
        return "historico da carteira";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public double getValor_unit() {
        return valor_unit;
    }

    public void setValor_unit(double valor_unit) {
        this.valor_unit = valor_unit;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public java.sql.Date getDia() {
        return dia;
    }

    public void setDia(java.sql.Date dia) {
        this.dia = dia;
    }

    private double valor_unit;

    private int quantidade;

    public boolean isCompra() {
        return compra;
    }

    public boolean isVenda(){
        return !compra;
    }

    public void setCompra(boolean compra) {
        this.compra = compra;
    }

    private boolean compra;

    private java.sql.Date dia;



}
