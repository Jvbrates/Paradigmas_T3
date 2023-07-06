package entidades;

import infra.entidades.Registro;

;

public class usuario implements Registro {

    private long id;

    private double saldo;

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getPassswd() {
        return passswd;
    }

    public void setPassswd(String passswd) {
        this.passswd = passswd;
    }

    public String passswd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;



    @Override
    public String getRotulo() {
        return "Usuario";
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id= id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                ", id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
