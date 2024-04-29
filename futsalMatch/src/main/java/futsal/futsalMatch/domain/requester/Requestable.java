package futsal.futsalMatch.domain.requester;

import futsal.futsalMatch.domain.data.MatchInfo;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface Requestable {
    List<MatchInfo> requestMatchInfo(LocalDate date, Integer region);
}
