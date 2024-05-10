package futsal.futsalMatch.domain.requester;

import futsal.futsalMatch.domain.converter.MatchInfoConverter;
import futsal.futsalMatch.configs.PuzzleConfig;
import futsal.futsalMatch.domain.data.MatchInfo;
import futsal.futsalMatch.domain.data.record.PuzzleMatchInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PuzzleRequester extends MatchInfoRequester {

    public PuzzleRequester(String baseURLString) {
        super(baseURLString);
    }

    @Override
    public List<MatchInfo> requestMatchInfo(LocalDate date, Integer region) {
        List<MatchInfo> matchInfoList = new ArrayList<>();
        /******************request header***************/
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Content-Type", "application/json");
        headers.set("Host", "puzzleplay.kr");
        headers.set("Origin", "https://puzzleplay.kr");
        headers.set("Referer", "https://puzzleplay.kr/social");
        /**********************************************/

        /*******************request body****************/
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("match_date", date.toString());
        jsonObject.put("XHR", true);
        jsonObject.put("active_date", date.toString());

        List<String> regionArr = new ArrayList<>();
        if(region == 0){
            regionArr.add(PuzzleConfig.Region.SEOUL);
            regionArr.add(PuzzleConfig.Region.GYEONGGI_NAMBU);
            regionArr.add(PuzzleConfig.Region.GYEONGGI_DONGBU);
            headers.set("Content-Length", "157");
        }
        if(region == 1){
            regionArr.add(PuzzleConfig.Region.SEOUL);
            headers.set("Content-Length", "103");
        }
        if(region == 2){
            regionArr.add(PuzzleConfig.Region.GYEONGGI_NAMBU);
            regionArr.add(PuzzleConfig.Region.GYEONGGI_DONGBU);
            headers.set("Content-Length", "130");
        }
        jsonObject.put("region", regionArr);
        /**********************************************/

        /********************send request**************/
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try{
            response = restTemplate.exchange(baseURLString, HttpMethod.POST, entity, String.class);
        } catch (Exception e){
            log.error("****************************퍼즐플레이 요청 실패***********************************\n");
            log.error(e.getMessage(), e);
            return matchInfoList;
        }

        /********************************************/

        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            JSONObject responseJson = new JSONObject(response.getBody());
            JSONArray puzzleMatchInfoList = responseJson.getJSONArray("list");
            for (Object object : puzzleMatchInfoList) {
                //System.out.println(object.toString());
                PuzzleMatchInfo puzzleMatchInfo = new PuzzleMatchInfo(new JSONObject(object.toString()));
                matchInfoList.add(MatchInfoConverter.convert(puzzleMatchInfo));
            }
        }

        return matchInfoList;
    }
}
