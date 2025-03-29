package futsal.futsalMatch.service.strategy.request;

import futsal.futsalMatch.config.platform.PlatformConfig;
import futsal.futsalMatch.enums.Region;
import futsal.futsalMatch.exception.UnexpectedResponseStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrbanRequestStrategy implements RequestStrategy {

    private final RestTemplate restTemplate;

    @Override
    public List<Object> request(PlatformConfig config, LocalDate date, Region region) {
        HttpHeaders requestHeaders = buildHttpHeaders();
        String requestBody = "mode=get_goods_list&date=" + date.toString() + "&area=11";

        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    config.getRequestBaseURL(),
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody, requestHeaders),
                    String.class
            );

            if(response.getStatusCode() != HttpStatus.OK){
                throw new UnexpectedResponseStatusException("Unexpected response status: " + response.getStatusCode());
            }

            Document document = Jsoup.parse(response.getBody());
            Elements matchElements = document.select("ul.goods_table_item");

            String dateElement = "<span class='date'>" + date + "</span>";

            return matchElements.stream()
                    .map(matchElement -> (Object) (matchElement + dateElement))
                    .toList();
        } catch (Exception e){
            log.error("어반풋볼 요청 실패 : {} - {}", e.getClass().getSimpleName(), e.getMessage());
            return List.of();
        }
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Content-Length", "43");
        headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.set("Host", "urbanfootball.co.kr");
        headers.set("Origin", "https://urbanfootball.co.kr");
        headers.set("Referer", "https://urbanfootball.co.kr/main/index.html");
        return headers;
    }
}
