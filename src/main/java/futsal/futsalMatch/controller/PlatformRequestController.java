package futsal.futsalMatch.controller;

import futsal.futsalMatch.domain.MatchInfo;
import futsal.futsalMatch.enums.Region;
import futsal.futsalMatch.service.PlatformRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "https://futsalfinder.co.kr/")
@RequiredArgsConstructor
@RestController
public class PlatformRequestController {
    private final PlatformRequestService platformRequestService;

    @GetMapping(value = "/matches/{date}", headers = {"Accept=application/json;charset=UTF-8"})
    public ResponseEntity<List<MatchInfo>> getMatchInfo(
            @PathVariable("date") LocalDate date,
            @RequestParam(value = "region") Integer region
    ) {
        return ResponseEntity.ok(platformRequestService.fetchAllData(date, Region.SEOUL));
    }
}
