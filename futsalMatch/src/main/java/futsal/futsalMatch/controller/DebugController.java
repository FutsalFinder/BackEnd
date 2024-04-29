package futsal.futsalMatch.controller;

import futsal.futsalMatch.configs.IamConfig;
import futsal.futsalMatch.configs.PlabConfig;
import futsal.futsalMatch.configs.PuzzleConfig;
import futsal.futsalMatch.configs.WithConfig;
import futsal.futsalMatch.domain.data.MatchInfo;
import futsal.futsalMatch.domain.requester.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class DebugController {
    @GetMapping("/matches/plab/{date}")
    public List<MatchInfo> showPlab(@PathVariable("date") String dateString) {
        Requestable requester = new PlabRequester(PlabConfig.baseUrl);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        return requester.requestMatchInfo(localDate, "1"); //1 is seoul in plab
    }

    @GetMapping("/matches/with/{date}")
    public List<MatchInfo> showWith(@PathVariable("date") String dateString) {
        Requestable requester = new WithRequester(WithConfig.baseUrl);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        return requester.requestMatchInfo(localDate, "0"); //0 is seoul in with
    }

    @GetMapping("/matches/puzzle/{date}")
    public List<MatchInfo> showPuzzle(@PathVariable("date") String dateString) {
        Requestable requester = new PuzzleRequester(PuzzleConfig.baseUrl);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        return requester.requestMatchInfo(localDate, "0"); //0 is seoul in with
    }

    @GetMapping("/matches/iam/{date}")
    public List<MatchInfo> showIam(@PathVariable("date") String dateString) {
        Requestable requester = new IamRequester(IamConfig.baseUrl);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        return requester.requestMatchInfo(localDate, "0"); //0 is seoul in with
    }
}
