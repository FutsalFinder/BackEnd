package futsal.futsalMatch.service.strategy.request;

import futsal.futsalMatch.config.platform.PlatformConfig;
import futsal.futsalMatch.enums.Region;
import futsal.futsalMatch.exception.UnexpectedResponseStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlabRequestStrategy implements RequestStrategy {

    private final RestTemplate restTemplate;

    @Override
    public List<Object> request(PlatformConfig config, LocalDate date, Region region) {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(config.getRequestBaseURL())
                .queryParam("sch", date)
                .queryParam("region", "1") //TODO region 값 변환
                .queryParam("page_size", "700")
                .queryParam("ordering", "schedule")
                .build().toString();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    String.class
            );

            if(response.getStatusCode() != HttpStatus.OK){
                throw new UnexpectedResponseStatusException("Unexpected response status: " + response.getStatusCode());
            }

            JSONArray jsonArray = new JSONArray(response.getBody());

            // Note: JSONArray.toList()는 사용하지 말 것.
            // 사용 시 JSONArray의 데이터를 List<Map>으로 변환하므로, 이후 JSONObject로 처리할 수 없음.
            return IntStream.range(0, jsonArray.length())
                    .mapToObj(jsonArray::get)
                    .toList();
        } catch (Exception e){
            log.error("플랩풋볼 요청 실패 : {} - {}", e.getClass().getSimpleName(), e.getMessage());
            return List.of();
        }
    }
}
