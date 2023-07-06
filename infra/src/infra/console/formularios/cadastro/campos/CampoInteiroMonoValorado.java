package infra.console.formularios.cadastro.campos;

public class CampoInteiroMonoValorado extends Campo{
    private int valor;
    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
    public CampoInteiroMonoValorado(String rotulo, int minimo, int maximo) {
        super(rotulo, minimo, maximo);
    }
}
