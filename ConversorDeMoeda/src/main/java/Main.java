import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.text.NumberFormat;

public class Main {

    private static final ConversorAPI conversorAPI = new ConversorAPI();
    private static final List<RegistroConversao> historico = new ArrayList<>(); // Lista pra guardar o histórico

    /**
     * Formata um valor numérico para a representação de moeda.
     * @param valor O valor a ser formatado.
     * @param codigoMoeda O código da moeda (ex: "USD", "BRL").
     * @return O valor formatado (ex: "R$ 100,00" ou "US$ 19,80").
     */
    private static String formatarMoeda(double valor, String codigoMoeda) {
        // Cria um objeto NumberFormat específico para Moeda
        NumberFormat formatador = NumberFormat.getCurrencyInstance();

        // Define o código da moeda a ser usado (ex: BRL, USD)
        // Isso garante que o símbolo (R$, US$, etc.) e a localização sejam corretos.
        formatador.setCurrency(java.util.Currency.getInstance(codigoMoeda));

        return formatador.format(valor);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        String menu = """
            =========================================
            👋 BEM-VINDO(A) AO CONVERSOR DE MOEDAS!
            =========================================
            
            Escolha uma opção de conversão:
            1.  Dólar (USD) >> Real (BRL)
            2.  Euro (EUR) >> Dólar (USD)
            3.  Real (BRL) >> Dólar (USD)
            4.  Iene (JPY) >> Euro (EUR)
            5.  Dirham (DEA) >> Dólar (USD)
            6.  Dólar canadense (CAD) >> Real (BRL)
            
            7.  Outra Conversão (Moedas Livres)
            8.  📄 Visualizar Histórico
            9.  ❌ Sair
            
            Opção:
            """;

        while (true) {
            System.out.println(menu);

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                if (opcao == 9) break;

                String moedaOrigem = "";
                String moedaDestino = "";

                switch (opcao) {
                    case 1: moedaOrigem = "USD"; moedaDestino = "BRL"; break;
                    case 2: moedaOrigem = "EUR"; moedaDestino = "USD"; break;
                    case 3: moedaOrigem = "BRL"; moedaDestino = "USD"; break;
                    case 4: moedaOrigem = "JPY"; moedaDestino = "EUR"; break;
                    case 5: moedaOrigem = "DEA"; moedaDestino = "USD"; break;
                    case 6: moedaOrigem = "CAD"; moedaDestino = "BRL"; break;
                    case 7:
                        System.out.print("Digite o código da Moeda de Origem (ex: USD): ");
                        moedaOrigem = scanner.nextLine().toUpperCase();
                        System.out.print("Digite o código da Moeda de Destino (ex: BRL): ");
                        moedaDestino = scanner.nextLine().toUpperCase();
                        break;
                    case 8:
                        visualizarHistorico();
                        continue;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        continue;
                }

                System.out.printf("Digite o valor em %s para converter: ", moedaOrigem);
                double valor = Double.parseDouble(scanner.nextLine());

                converter(valor, moedaOrigem, moedaDestino);

            } catch (NumberFormatException e) {
                System.out.println("❌ Erro: Por favor, digite um número válido para a opção ou valor.");
            } catch (RuntimeException e) {
                String mensagemErro = e.getMessage();
                if (mensagemErro != null && mensagemErro.contains("Status 404")) {
                    System.out.println("\n🛑 ERRO DE MOEDA: Um dos códigos de moeda digitados não é válido ou não é suportado pela API.");
                } else {
                    System.out.println("❌ Erro na Conversão (API ou Conexão): " + mensagemErro);
                }
            } catch (Exception e) {
                System.out.println("❌ Erro Inesperado: " + e.getMessage());
            }
        }

        System.out.println("\nPrograma encerrado. Até mais! 👋");
        scanner.close();
    }

    private static void converter(double valor, String moedaOrigem, String moedaDestino)
            throws IOException, InterruptedException {

        RecordExchangeRate taxas = conversorAPI.buscarTaxas(moedaOrigem);

        // 2. Verifica se a taxa de destino existe no Map
        if (!taxas.conversion_rates().containsKey(moedaDestino)) {
            System.out.printf("❌ Erro: Moeda de destino '%s' não encontrada na API (Taxas incompletas).%n", moedaDestino);
            return;
        }

        double taxa = taxas.conversion_rates().get(moedaDestino);
        double resultado = valor * taxa;

        // 5. Salva o registro no histórico
        RegistroConversao novoRegistro = new RegistroConversao(
                valor,
                moedaOrigem,
                resultado,
                moedaDestino,
                taxa,
                LocalDateTime.now()
        );
        historico.add(novoRegistro);

        // 6. Exibe o resultado formatado
        String valorFormatadoOrigem = formatarMoeda(valor, moedaOrigem);
        String valorFormatadoDestino = formatarMoeda(resultado, moedaDestino);

        System.out.printf("\n✅ Resultado da Conversão: %s equivalem a %s%n\n",
                valorFormatadoOrigem, valorFormatadoDestino);
    }

    private static void visualizarHistorico() {
        if (historico.isEmpty()) {
            System.out.println("\n🚫 O histórico de conversões está vazio.");
            return;
        }

        System.out.println("\n=========================================");
        System.out.println("         📜 HISTÓRICO DE CONVERSÕES");
        System.out.println("=========================================");

        for (RegistroConversao registro : historico) {
            System.out.println(registro);
        }
        System.out.println("=========================================\n");
    }
}