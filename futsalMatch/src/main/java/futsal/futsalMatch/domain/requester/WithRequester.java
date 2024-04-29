package futsal.futsalMatch.domain.requester;

import futsal.futsalMatch.domain.converter.MatchInfoConverter;
import futsal.futsalMatch.domain.data.MatchInfo;
import futsal.futsalMatch.domain.data.record.WithMatchInfo;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WithRequester extends MatchInfoRequester {
    public WithRequester(String baseURLString){
        super(baseURLString);
    }

    @Override
    public List<MatchInfo> requestMatchInfo(LocalDate date, Integer region) {
        List<MatchInfo> matchInfoList = new ArrayList<>();
        addQueryParam("cmd", "search-info");
        addQueryParam("day", date.toString());
        addQueryParam("area_code", region);
        addQueryParam("member_code", "all");

        HttpEntity<String> httpEntity = new HttpEntity<>(new HttpHeaders());
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate
                .exchange(getRequestUrlString(), HttpMethod.GET, httpEntity, String.class);

        HttpStatusCode httpStatusCode = response.getStatusCode();
        if (httpStatusCode == HttpStatus.OK) {
            JSONObject jsonData = new JSONObject(response.toString().substring(11)); //starts with "200 OK OK,{JsonData...}"
            JSONArray withMatchInfoList = new JSONArray(jsonData.get("block_list").toString());
            for (Object object : withMatchInfoList) {
                WithMatchInfo withMatchInfo = new WithMatchInfo(new JSONObject(object.toString()));
                matchInfoList.add(MatchInfoConverter.convert(withMatchInfo));
            }
        }
        else {
            System.out.println("요청 실패: " + httpStatusCode);
        }

        return matchInfoList;
    }
}
