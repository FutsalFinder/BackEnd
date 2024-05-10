package futsal.futsalMatch.domain.requester;

import futsal.futsalMatch.domain.converter.MatchInfoConverter;
import futsal.futsalMatch.domain.data.MatchInfo;
import futsal.futsalMatch.domain.data.record.WithMatchInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WithRequester extends MatchInfoRequester {
    public WithRequester(String baseURLString){
        super(baseURLString);
    }

    @Override
    public List<MatchInfo> requestMatchInfo(LocalDate date, Integer region) {
        List<MatchInfo> matchInfoList = new ArrayList<>();
        if(region == 2) return matchInfoList; //위드풋살은 서울만 가능

        List<String> paramList = new ArrayList<>();
        addQueryParam(paramList,"cmd", "search-info");
        addQueryParam(paramList,"day", date.toString());

        addQueryParam(paramList,"area_code", "0");
        addQueryParam(paramList,"member_code", "all");

        HttpEntity<String> httpEntity = new HttpEntity<>(new HttpHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try{
            response = restTemplate
                    .exchange(getRequestUrlString(paramList), HttpMethod.GET, httpEntity, String.class);
        } catch (Exception e){
            log.error("****************************위드풋살 요청 실패***********************************\n");
            log.error(e.getMessage(), e);
            return matchInfoList;
        }

        HttpStatusCode httpStatusCode = response.getStatusCode();
        if (httpStatusCode == HttpStatus.OK) {
            try{
                JSONObject jsonData = new JSONObject(response.toString().substring(11)); //starts with "200 OK OK,{JsonData...}"
                JSONArray withMatchInfoList = new JSONArray(jsonData.get("block_list").toString());
                for (Object object : withMatchInfoList) {
                    WithMatchInfo withMatchInfo = new WithMatchInfo(new JSONObject(object.toString()));
                    matchInfoList.add(MatchInfoConverter.convert(withMatchInfo));
                }
            } catch(JSONException ignored){}

        }

        return matchInfoList;
    }
}
