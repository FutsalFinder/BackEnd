package futsal.futsalMatch.requester;

import futsal.futsalMatch.converter.MatchInfoConverter;
import futsal.futsalMatch.configs.PuzzleConfig;
import futsal.futsalMatch.domain.MatchInfos.MatchInfo;
import futsal.futsalMatch.domain.MatchInfoRequester;
import futsal.futsalMatch.domain.MatchInfos.record.PuzzleMatchInfo;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PuzzleRequester extends MatchInfoRequester {

    public PuzzleRequester(String baseURLString) {
        super(baseURLString);
    }

    @Override
    public List<MatchInfo> requestMatchInfo(LocalDate date, @Nullable String region) {
        List<MatchInfo> matchInfoList = new ArrayList<>();
        /******************request header***************/
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Content-Length", "103");
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
        regionArr.add(PuzzleConfig.PuzzleRegion.SEOUL);
        jsonObject.put("region", regionArr);
        /**********************************************/

        /********************send request**************/
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(baseURLString, HttpMethod.POST, entity, String.class);
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
        } else {
            System.out.println("요청 실패: " + statusCode);
        }

        return matchInfoList;
    }
}
