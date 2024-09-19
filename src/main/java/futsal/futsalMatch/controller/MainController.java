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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@Slf4j
@CrossOrigin(origins = "https://futsalfinder.co.kr/")
@RestController
public class MainController {
    private static List<Requestable> requesters = new ArrayList<>();
    static {
        requesters.add(new PlabRequester(PlabConfig.baseUrl));
        requesters.add(new PuzzleRequester(PuzzleConfig.baseUrl));
        requesters.add(new WithRequester(WithConfig.baseUrl));
        requesters.add(new IamRequester(IamConfig.baseUrl));
    }

    @GetMapping(value = "/matches/{date}", headers = {"Accept=application/json;charset=UTF-8"})
    public List<MatchInfo> showAll(@PathVariable("date") LocalDate date,
                                   @RequestParam(value = "region") Integer region) {

        //long startTime = System.currentTimeMillis();

        List<RequestThread> threads = new ArrayList<>();

        List<MatchInfo> matchInfoList = Collections.synchronizedList(new ArrayList<>());
        for (Requestable requester : requesters) {
            threads.add(new RequestThread(requester, matchInfoList, date, region));
        }

        for(RequestThread requestThread : threads){
            requestThread.start();
        }

        for(RequestThread requestThread : threads){
            try {
                requestThread.join();
            } catch (InterruptedException e) {
                log.error("Thread interrupted", e);
                Thread.currentThread().interrupt();
            }
        }

        matchInfoList.sort((m1, m2) ->
                LocalTime.parse(m1.getTime())
                        .compareTo(LocalTime.parse(m2.getTime())));

        ZonedDateTime nowDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        if(date.equals(nowDateTime.toLocalDate())){
            matchInfoList.removeIf(m -> nowDateTime.toLocalTime().isAfter(LocalTime.parse(m.getTime())));
        }

        //long endTime = System.currentTimeMillis();
        //long duration = endTime - startTime;

        //System.out.println("소요 시간: " + duration + " 밀리초");
        //System.out.println(duration);
        return matchInfoList;
    }

    @AllArgsConstructor
    class RequestThread extends Thread{
        Requestable requester;
        List<MatchInfo> matchInfoList;
        LocalDate date;
        Integer region;
        @Override
        public void run() {
            try{
                matchInfoList.addAll(requester.requestMatchInfo(date, region));
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
    }
}