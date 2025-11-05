import java.util.Map;

public record RecordExchangeRate(
        String result,
        String documentation,
        String base_code,
        Map<String, Double> conversion_rates
) {

}