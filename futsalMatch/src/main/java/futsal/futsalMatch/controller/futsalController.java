package futsal.futsalMatch.controller;

import futsal.futsalMatch.requester.IamRequester;
import futsal.futsalMatch.requester.PlabRequester;
import futsal.futsalMatch.requester.PuzzleRequester;
import futsal.futsalMatch.requester.WithRequester;
import futsal.futsalMatch.domain.MatchInfos.MatchInfo;
import futsal.futsalMatch.domain.Requestable;
import futsal.futsalMatch.configs.IamConfig;
import futsal.futsalMatch.configs.PlabConfig;
import futsal.futsalMatch.configs.PuzzleConfig;
import futsal.futsalMatch.configs.WithConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class futsalController {

    class sortByMatchStartTime implements Comparator<MatchInfo> {
        @Override
        public int compare(MatchInfo m1, MatchInfo m2) {
            return LocalTime.parse(m1.getTime()).compareTo(LocalTime.parse(m2.getTime()));
        }
    }

    @ResponseBody
    @GetMapping("/futsal-info/{date}")
    public List<MatchInfo> showAll(@PathVariable("date") String dateString) {
        List<MatchInfo> matchInfoList = new ArrayList<>();
        Requestable plabRequester = new PlabRequester(PlabConfig.baseUrl);
        Requestable puzzleRequester = new PuzzleRequester(PuzzleConfig.baseUrl);
        Requestable withRequester = new WithRequester(WithConfig.baseUrl);
        Requestable iamRequester = new IamRequester(IamConfig.baseUrl);

        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        matchInfoList.addAll(plabRequester.requestMatchInfo(localDate, "1"));
        matchInfoList.addAll(puzzleRequester.requestMatchInfo(localDate, "0"));
        matchInfoList.addAll(withRequester.requestMatchInfo(localDate, "0"));
        matchInfoList.addAll(iamRequester.requestMatchInfo(localDate, "0"));

        matchInfoList.sort(new sortByMatchStartTime());
        return matchInfoList;
    }

    @ResponseBody
    @GetMapping("/futsal-info/plab/{date}")
    public List<MatchInfo> showPlab(@PathVariable("date") String dateString) {
        Requestable requester = new PlabRequester(PlabConfig.baseUrl);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        return requester.requestMatchInfo(localDate, "1"); //1 is seoul in plab
    }

    @ResponseBody
    @GetMapping("/futsal-info/with/{date}")
    public List<MatchInfo> showWith(@PathVariable("date") String dateString) {
        Requestable requester = new WithRequester(WithConfig.baseUrl);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        return requester.requestMatchInfo(localDate, "0"); //0 is seoul in with
    }

    @ResponseBody
    @GetMapping("/futsal-info/puzzle/{date}")
    public List<MatchInfo> showPuzzle(@PathVariable("date") String dateString) {
        Requestable requester = new PuzzleRequester(PuzzleConfig.baseUrl);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        return requester.requestMatchInfo(localDate, "0"); //0 is seoul in with
    }

    @ResponseBody
    @GetMapping("/futsal-info/iam/{date}")
    public List<MatchInfo> showIam(@PathVariable("date") String dateString) {
        Requestable requester = new IamRequester(IamConfig.baseUrl);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        return requester.requestMatchInfo(localDate, "0"); //0 is seoul in with
    }
}
