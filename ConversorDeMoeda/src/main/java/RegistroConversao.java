import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Esta é a versão CLASSICA (Funciona em todas as versões do Java)
public class RegistroConversao {
    private final double valorOriginal;
    private final String moedaOrigem;
    private final double valorConvertido;
    private final String moedaDestino;
    private final double taxaUsada;
    private final LocalDateTime dataHora;

    // Construtor
    public RegistroConversao(double valorOriginal, String moedaOrigem, double valorConvertido, String moedaDestino, double taxaUsada, LocalDateTime dataHora) {
        this.valorOriginal = valorOriginal;
        this.moedaOrigem = moedaOrigem;
        this.valorConvertido = valorConvertido;
        this.moedaDestino = moedaDestino;
        this.taxaUsada = taxaUsada;
        this.dataHora = dataHora;
    }

    // Método para formatar a exibição do registro
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format(
                "[%s] %.2f %s -> %.2f %s (Taxa: %.4f)",
                dataHora.format(formatter),
                valorOriginal,
                moedaOrigem,
                valorConvertido,
                moedaDestino,
                taxaUsada
        );
    }

    // Getters (Adicionados apenas para garantir que o código compile, mesmo que não usemos)
    public double valorOriginal() { return valorOriginal; }
    public String moedaOrigem() { return moedaOrigem; }
    public double valorConvertido() { return valorConvertido; }
    public String moedaDestino() { return moedaDestino; }
    public double taxaUsada() { return taxaUsada; }
    public LocalDateTime dataHora() { return dataHora; }
}