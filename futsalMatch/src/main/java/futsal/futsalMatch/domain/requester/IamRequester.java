package futsal.futsalMatch.domain.requester;

import futsal.futsalMatch.domain.converter.MatchInfoConverter;
import futsal.futsalMatch.domain.data.record.IamMatchInfo;
import futsal.futsalMatch.domain.data.MatchInfo;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IamRequester extends MatchInfoRequester {

    public IamRequester(String basicURL) {
        super(basicURL);
    }

    @Override
    public List<MatchInfo> requestMatchInfo(LocalDate date, Integer region) {
        List<MatchInfo> matchInfoList = new ArrayList<>();
        /******************request header***************/
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.set("Content-Length", "100");
        headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.set("Host", "m.iamground.kr");
        headers.set("Origin", "https://m.iamground.kr");
        headers.set("Referer", "https://m.iamground.kr/futsal/s_match/search");
        /**********************************************/

        /*******************request body****************/
        String requestBody = "date=" + date.toString() + "&rule=rule&gender_type=gender_type&lev_type=lev_type&park=X&subway=X&load_type=search";
        /**********************************************/

        /********************send request**************/
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try{
            response = restTemplate.exchange(baseURLString, HttpMethod.POST, entity, String.class);
        } catch (Exception e){
            log.error("****************************아이엠그라운드 요청 실패***********************************\n");
            log.error(e.getMessage(), e);
            return matchInfoList;
        }
        /********************************************/

        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            JSONArray iamMatchInfoList = new JSONArray(response.toString().substring(11)); //starts with "<200 OK OK,[JsonArray...]"
            for (Object object : iamMatchInfoList) {
                IamMatchInfo iamMatchInfo = new IamMatchInfo(new JSONObject(object.toString()));
                MatchInfo matchInfo = MatchInfoConverter.convert(iamMatchInfo);
                if(region == 1 && !matchInfo.getRegion().equals("서울")) continue;
                if(region == 2 && !matchInfo.getRegion().equals("경기")) continue;
                matchInfoList.add(matchInfo);
            }
        }

        return matchInfoList;
    }
}
