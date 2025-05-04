package futsal.futsalMatch.service.strategy.parsing;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class PlabParsingStrategy implements ParsingStrategy {
    @Override
    public List<Object> parse(String fetchData, LocalDate date) {
        JSONObject jsonData = new JSONObject(fetchData);

        if(!jsonData.has("results")) {
            return List.of(); //매치가 없을 때
        }

        JSONArray jsonArray = jsonData.getJSONArray("results");
        return IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::get)
                .toList();
    }
}
