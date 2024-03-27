package futsal.futsalMatch.domain;

import futsal.futsalMatch.domain.MatchInfos.MatchInfo;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface Requestable {
    List<MatchInfo> requestMatchInfo(LocalDate date, @Nullable String region);
}
