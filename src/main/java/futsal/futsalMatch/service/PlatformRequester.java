package futsal.futsalMatch.service;

import futsal.futsalMatch.config.platform.PlatformConfig;
import futsal.futsalMatch.domain.MatchInfo;
import futsal.futsalMatch.enums.Region;
import futsal.futsalMatch.service.strategy.parsing.ParsingStrategy;
import futsal.futsalMatch.service.strategy.request.RequestStrategy;
import futsal.futsalMatch.service.strategy.transform.TransformStrategy;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class PlatformRequester {
    private final RequestStrategy requestStrategy;
    private final ParsingStrategy parsingStrategy;
    private final TransformStrategy transformStrategy;
    private final PlatformConfig config;

    public String fetch(LocalDate date, Region region) {
        return requestStrategy.fetch(config, date, region);
    }

    public List<Object> parse(String fetchData, LocalDate date) {
        return parsingStrategy.parse(fetchData, date);
    }

    public List<MatchInfo> transform(List<Object> matchList) {
        return matchList.stream().map(data -> transformStrategy.transform(config, data)).toList();
    }

    public List<MatchInfo> request(LocalDate date, Region region) {
        return requestStrategy.request(config, date, region)
                .stream()
                .map(m -> transformStrategy.transform(config, m))
                .toList();
    }
}
