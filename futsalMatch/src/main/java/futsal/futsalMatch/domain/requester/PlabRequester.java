package futsal.futsalMatch.domain.requester;
import futsal.futsalMatch.domain.converter.MatchInfoConverter;
import futsal.futsalMatch.domain.data.MatchInfo;
import futsal.futsalMatch.domain.data.record.PlabMatchInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

        List<String> paramList = new ArrayList<>();
        addQueryParam(paramList,"sch", date.toString());
        addQueryParam(paramList,"region", region.toString());
        addQueryParam(paramList,"page_size", "700");
        addQueryParam(paramList,"ordering", "schedule");

        HttpEntity<String> httpEntity = new HttpEntity<>(new HttpHeaders());
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response;

        try{
            response = restTemplate
                    .exchange(getRequestUrlString(paramList), HttpMethod.GET, httpEntity, String.class);
        } catch (Exception e){
            log.error("****************************플랩풋볼 요청 실패***********************************\n");
            log.error(e.getMessage(), e);
            return matchInfoList;
        }

        HttpStatusCode httpStatusCode = response.getStatusCode();
        if (httpStatusCode == HttpStatus.OK) {
            JSONArray plabMatchInfoList = new JSONArray(response.getBody());
            for (Object object : plabMatchInfoList) {
                PlabMatchInfo plabMatchInfo = new PlabMatchInfo(new JSONObject(object.toString()));
                matchInfoList.add(MatchInfoConverter.convert(plabMatchInfo));
            }
        }

        return matchInfoList;
    }
}

