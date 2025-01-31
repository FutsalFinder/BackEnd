package futsal.futsalMatch.service;

import futsal.futsalMatch.domain.MatchInfo;
import futsal.futsalMatch.enums.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PlatformRequestService {

    private final List<PlatformRequester> requesters;

    public List<MatchInfo> fetchAllData(LocalDate date, Region region) {
        List<CompletableFuture<List<MatchInfo>>> futures = requesters.stream()
                .map(requester -> CompletableFuture.supplyAsync(() -> requester.request(date, region)))
                .toList();

        CompletableFuture<List<MatchInfo>> result = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .filter(m -> isValidTime(m.getTime()))
                        .sorted(Comparator.comparing(m -> LocalTime.parse(m.getTime())))
                        .toList()
                );

        try {
            return removeExpiredMatch(date, result.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to fetch data", e);
        }
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

    private List<MatchInfo> removeExpiredMatch(LocalDate requestDate, List<MatchInfo> list) {
        ZonedDateTime nowDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        //요청 날짜가 오늘인 경우
        if(requestDate.equals(nowDateTime.toLocalDate())){
            // 현재 시간을 기준으로 이미 종료된 매치를 제거
            // 불변 객체라 stream 사용
            return list.stream()
                    .filter(m -> LocalTime.parse(m.getTime()).isAfter(nowDateTime.toLocalTime()))
                    .toList();
        }

        return list;
    }

}
