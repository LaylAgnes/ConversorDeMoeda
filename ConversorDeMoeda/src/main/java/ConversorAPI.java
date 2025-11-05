import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorAPI {

    // MINHA CHAVE DE API
    private static final String API_KEY = "ce73dcd17576870ee606835d";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private final Gson gson = new Gson();
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Busca as taxas de câmbio mais recentes usando uma moeda base.
     * @param moedaBase O código da moeda base (ex: "USD", "BRL").
     * @return Um objeto RecordExchangeRate contendo as taxas.
     * @throws IOException Se houver erro de I/O na requisição.
     * @throws InterruptedException Se a requisição for interrompida.
     */
    public RecordExchangeRate buscarTaxas(String moedaBase) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/latest/" + moedaBase;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Envia a requisição e espera pela resposta
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        // Verifica o código de status HTTP
        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao buscar taxas: Status " + response.statusCode());
        }

        // Converte o JSON (String) para o objeto Java (RecordExchangeRate)
        return gson.fromJson(response.body(), RecordExchangeRate.class);
    }
}