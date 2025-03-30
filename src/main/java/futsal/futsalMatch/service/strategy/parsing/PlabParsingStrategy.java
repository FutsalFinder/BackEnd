package futsal.futsalMatch.service.strategy.parsing;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class PlabParsingStrategy implements ParsingStrategy {
    @Override
    public List<Object> parse(String fetchData, LocalDate date) {
        JSONArray jsonArray = new JSONArray(fetchData);
        return IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::get)
                .toList();
    }
}
