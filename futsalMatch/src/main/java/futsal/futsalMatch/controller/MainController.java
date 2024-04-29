package futsal.futsalMatch.controller;

import futsal.futsalMatch.domain.requester.IamRequester;
import futsal.futsalMatch.domain.requester.PlabRequester;
import futsal.futsalMatch.domain.requester.PuzzleRequester;
import futsal.futsalMatch.domain.requester.WithRequester;
import futsal.futsalMatch.domain.data.MatchInfo;
import futsal.futsalMatch.domain.requester.Requestable;
import futsal.futsalMatch.configs.IamConfig;
import futsal.futsalMatch.configs.PlabConfig;
import futsal.futsalMatch.configs.PuzzleConfig;
import futsal.futsalMatch.configs.WithConfig;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class MainController {
    private static List<Requestable> requesters = new ArrayList<>();
    static {
        requesters.add(new PlabRequester(PlabConfig.baseUrl));
        requesters.add(new PuzzleRequester(PuzzleConfig.baseUrl));
        requesters.add(new WithRequester(WithConfig.baseUrl));
        requesters.add(new IamRequester(IamConfig.baseUrl));
    }
    @GetMapping("/matches/{date}")
    public List<MatchInfo> showAll(@PathVariable("date") LocalDate date,
                                   @RequestParam(value = "region") Integer region) {
        List<MatchInfo> matchInfoList = new ArrayList<>();
        for (Requestable requester : requesters) {
            try{
                matchInfoList.addAll(requester.requestMatchInfo(date, region));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        matchInfoList.sort((m1, m2) ->
                LocalTime.parse(m1.getTime())
                        .compareTo(LocalTime.parse(m2.getTime())));

        if(date.equals(LocalDate.now())){
            matchInfoList.removeIf(m -> LocalTime.now().isAfter(LocalTime.parse(m.getTime())));
        }

        return matchInfoList;
    }
}
