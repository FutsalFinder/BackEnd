package futsal.futsalMatch.service;

import futsal.futsalMatch.domain.MatchInfo;
import futsal.futsalMatch.enums.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformRequestService {

    private final List<PlatformRequester> requesters;

    public List<MatchInfo> fetchAllData(LocalDate date, Region region) {
        List<MatchInfo> result = requesters.stream()
                .map(requester -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return requester.request(date, region);
                    } catch (Exception e) {
                        log.error("Error in {} - {}", requester.getClass().getSimpleName(), e.getMessage());
                        return List.<MatchInfo>of();
                    }
                }))
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .filter(m -> isValidTime(m.getTime()))
                .sorted(Comparator.comparing(m -> LocalTime.parse(m.getTime())))
                .toList();

        return removeExpiredMatch(date, result);
    }

    private boolean isValidTime(String time) {
        if(time == null) {
            return false;
        }

        try {
            LocalTime.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private List<MatchInfo> removeExpiredMatch(LocalDate date, List<MatchInfo> list) {
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        if(!date.equals(now.toLocalDate())) {
            return list;
        }

        return list.stream()
                .filter(m -> LocalTime.parse(m.getTime()).isAfter(now.toLocalTime()))
                .toList();
    }

}
