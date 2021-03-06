package pojos;
import java.io.Serializable;
import java.util.Date;

public class Voo implements Serializable{

    private float   preco;
    private int     qtdAcentos;
    private int     numeroVoo;
    private String  aeroportoPartida;
    private String  aeroportoChegada;
    private Date    dataSaida;
    private Date    dataChegada;

    public Voo(){
    }

    public int getQtdAcentos() {
        return qtdAcentos;
    }

    public void setQtdAcentos(int qtdAcentos) {
        this.qtdAcentos = qtdAcentos;
    }
    
    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getNumeroVoo() {
        return numeroVoo;
    }

    public void setNumeroVoo(int numeroVoo) {
        this.numeroVoo = numeroVoo;
    }

    public String getAeroportoPartida() {
        return aeroportoPartida;
    }

    public void setAeroportoPartida(String aeroportoPartida) {
        this.aeroportoPartida = aeroportoPartida;
    }

    public String getAeroportoChegada() {
        return aeroportoChegada;
    }

    public void setAeroportoChegada(String aeroportoChegada) {
        this.aeroportoChegada = aeroportoChegada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    
    
}

