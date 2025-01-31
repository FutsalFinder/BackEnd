package futsal.futsalMatch.service.strategy.request;

import futsal.futsalMatch.config.platform.PlatformConfig;
import futsal.futsalMatch.enums.Region;
import futsal.futsalMatch.exception.UnexpectedResponseStatusException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
public class PuzzleRequestStrategy implements RequestStrategy {
    @Override
    public List<Object> request(PlatformConfig config, LocalDate date, Region region) {
        HttpHeaders requestHeaders = buildHttpHeaders();
        JSONObject requestBody = buildRequestBody(config, date, region, requestHeaders);

        try{
            ResponseEntity<String> response = new RestTemplate().exchange(
                    config.getRequestBaseURL(),
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody.toString(), requestHeaders),
                    String.class
            );

            if(response.getStatusCode() != HttpStatus.OK){
                throw new UnexpectedResponseStatusException("Unexpected response status: " + response.getStatusCode());
            }

            JSONObject jsonData = new JSONObject(response.getBody());

            if(!jsonData.has("list")) {
                return List.of(); //매치가 없을 때
            }

            JSONArray jsonArray = jsonData.getJSONArray("list");

            // Note: JSONArray.toList()는 사용하지 말 것.
            // 사용 시 JSONArray의 데이터를 List<Map>으로 변환하므로, 이후 JSONObject로 처리할 수 없음.
            return IntStream.range(0, jsonArray.length())
                    .mapToObj(jsonArray::get)
                    .toList();
        } catch (Exception e){
            log.error("퍼즐플레이 요청 실패 : {}", e.getMessage());
            return List.of();
        }
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Content-Type", "application/json");
        headers.set("Host", "puzzleplay.kr");
        headers.set("Origin", "https://puzzleplay.kr");
        headers.set("Referer", "https://puzzleplay.kr/social");
        return headers;
    }

    private JSONObject buildRequestBody(PlatformConfig config, LocalDate date, Region region, HttpHeaders headers) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_date", date.toString());
        jsonObject.put("XHR", true);
        jsonObject.put("active_date", date.toString());

        List<String> regionArr = new ArrayList<>();
        final String SEOUL_CODE = config.getCustomFields().get("SEOUL_CODE");
        final String GYEONGGI_NAMBU_CODE = config.getCustomFields().get("GYEONGGI_NAMBU_CODE");
        final String GYEONGGI_DONGBU_CODE = config.getCustomFields().get("GYEONGGI_DONGBU_CODE");
        if(region == Region.ALL) {
            regionArr.add(SEOUL_CODE);
            regionArr.add(GYEONGGI_NAMBU_CODE);
            regionArr.add(GYEONGGI_DONGBU_CODE);
            headers.set("Content-Length", "157");
        } else if(region == Region.SEOUL){
            regionArr.add(SEOUL_CODE);
            headers.set("Content-Length", "103");
        } else if(region == Region.GYEONGGI){
            regionArr.add(GYEONGGI_NAMBU_CODE);
            regionArr.add(GYEONGGI_DONGBU_CODE);
            headers.set("Content-Length", "130");
        }

        jsonObject.put("region", regionArr);
        return jsonObject;
    }
}
