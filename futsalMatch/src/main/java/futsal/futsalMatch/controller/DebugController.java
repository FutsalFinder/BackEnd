package futsal.futsalMatch.controller;

import futsal.futsalMatch.configs.IamConfig;
import futsal.futsalMatch.configs.PlabConfig;
import futsal.futsalMatch.configs.PuzzleConfig;
import futsal.futsalMatch.configs.WithConfig;
import futsal.futsalMatch.domain.data.MatchInfo;
import futsal.futsalMatch.domain.requester.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class DebugController {
    @GetMapping("/matches/plab/{date}")
    public List<MatchInfo> showPlab(@PathVariable("date") LocalDate date,
                                    @RequestParam(defaultValue = "0") Integer region) {
        Requestable requester = new PlabRequester(PlabConfig.baseUrl);
        return requester.requestMatchInfo(date, region);
    }

    @GetMapping("/matches/with/{date}")
    public List<MatchInfo> showWith(@PathVariable("date") LocalDate date,
                                    @RequestParam(defaultValue = "0") Integer region) {
        Requestable requester = new WithRequester(WithConfig.baseUrl);
        return requester.requestMatchInfo(date, region);
    }

    @GetMapping("/matches/puzzle/{date}")
    public List<MatchInfo> showPuzzle(@PathVariable("date") LocalDate date,
                                      @RequestParam(defaultValue = "0") Integer region) {
        Requestable requester = new PuzzleRequester(PuzzleConfig.baseUrl);
        return requester.requestMatchInfo(date, region);
    }

    @GetMapping("/matches/iam/{date}")
    public List<MatchInfo> showIam(@PathVariable("date") LocalDate date,
                                   @RequestParam(defaultValue = "0") Integer region) {
        Requestable requester = new IamRequester(IamConfig.baseUrl);
        return requester.requestMatchInfo(date, region);
    }
}
