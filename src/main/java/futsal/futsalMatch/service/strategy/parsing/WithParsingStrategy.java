package futsal.futsalMatch.service.strategy.parsing;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class WithParsingStrategy implements ParsingStrategy {
    @Override
    public List<Object> parse(String fetchData, LocalDate date) {
        if(fetchData == null || fetchData.isEmpty()) {
            return List.of();
        }

        JSONObject jsonData = new JSONObject(fetchData.substring(11)); //starts with "200 OK OK,{JsonData...}"

        if(!jsonData.has("block_list")) {
            return List.of(); //매치가 없을 때
        }

        JSONArray jsonArray = jsonData.getJSONArray("block_list");

        // Note: JSONArray.toList()는 사용하지 말 것.
        // 사용 시 JSONArray의 데이터를 List<Map>으로 변환하므로, 이후 JSONObject로 처리할 수 없음.
        return IntStream.range(0, jsonArray.length())
                .mapToObj(jsonArray::get)
                .toList();
    }
}
