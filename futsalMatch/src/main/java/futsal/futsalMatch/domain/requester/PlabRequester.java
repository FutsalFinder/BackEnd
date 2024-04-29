package futsal.futsalMatch.domain.requester;
import futsal.futsalMatch.domain.converter.MatchInfoConverter;
import futsal.futsalMatch.domain.data.MatchInfo;
import futsal.futsalMatch.domain.data.record.PlabMatchInfo;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlabRequester extends MatchInfoRequester {
    public PlabRequester(String baseURLString){
        super(baseURLString);
    }

    @Override
    public List<MatchInfo> requestMatchInfo(LocalDate date, Integer region) {
        List<MatchInfo> matchInfoList = new ArrayList<>();
        if(region == 0){
            matchInfoList.addAll(requestByRegion(date, 1));
            matchInfoList.addAll(requestByRegion(date, 2));
        }
        else{
            matchInfoList.addAll(requestByRegion(date, region));
        }
        return matchInfoList;
    }

    private List<MatchInfo> requestByRegion(LocalDate date, Integer region) {
        List<MatchInfo> matchInfoList = new ArrayList<>();

        addQueryParam("sch", date.toString());
        addQueryParam("region", region.toString());
        addQueryParam("page_size", "700");
        addQueryParam("ordering", "schedule");

        HttpEntity<String> httpEntity = new HttpEntity<>(new HttpHeaders());
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate
                .exchange(getRequestUrlString(), HttpMethod.GET, httpEntity, String.class);

        HttpStatusCode httpStatusCode = response.getStatusCode();
        if (httpStatusCode == HttpStatus.OK) {
            JSONArray plabMatchInfoList = new JSONArray(response.getBody());
            for (Object object : plabMatchInfoList) {
                PlabMatchInfo plabMatchInfo = new PlabMatchInfo(new JSONObject(object.toString()));
                matchInfoList.add(MatchInfoConverter.convert(plabMatchInfo));
            }
        }
        else {
            System.out.println("요청 실패: " + httpStatusCode);
        }
        return matchInfoList;
    }
}

