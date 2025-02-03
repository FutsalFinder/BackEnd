package futsal.futsalMatch.service.strategy.transform;

import futsal.futsalMatch.config.platform.PlatformConfig;
import futsal.futsalMatch.domain.MatchInfo;

public abstract class TransformStrategy {
    public final MatchInfo transform(PlatformConfig config, Object matchData) {
        matchData = resolveObject(matchData);

        return MatchInfo.builder()
                .platform(resolvePlatform(config, matchData))
                .date(resolveDate(config, matchData))
                .time(resolveTime(config, matchData))
                .region(resolveRegion(config, matchData))
                .match_title(resolveMatchTitle(config, matchData))
                .main_stadium(resolveMainStadium(config, matchData))
                .sub_stadium(resolveSubStadium(config, matchData))
                .match_type(resolveMatchType(config, matchData))
                .sex(resolveSex(config, matchData))
                .level(resolveLevel(config, matchData))
                .match_vs(resolveMatchVS(config, matchData))
                .cur_player(resolveCurPlayer(config, matchData))
                .max_player(resolveMaxPlayer(config, matchData))
                .price(resolvePrice(config, matchData))
                .link(resolveLink(config, matchData))
                .build();
    }

    protected abstract Object resolveObject(Object matchData);
    protected abstract String resolvePlatform(PlatformConfig config, Object matchData);
    protected abstract String resolveDate(PlatformConfig config, Object matchData);
    protected abstract String resolveTime(PlatformConfig config, Object matchData);
    protected abstract String resolveRegion(PlatformConfig config, Object matchData);
    protected abstract String resolveMatchTitle(PlatformConfig config, Object matchData);
    protected abstract String resolveMainStadium(PlatformConfig config, Object matchData);
    protected abstract String resolveSubStadium(PlatformConfig config, Object matchData);
    protected abstract String resolveMatchType(PlatformConfig config, Object matchData);
    protected abstract String resolveSex(PlatformConfig config, Object matchData);
    protected abstract String resolveLevel(PlatformConfig config, Object matchData);
    protected abstract String resolveMatchVS(PlatformConfig config, Object matchData);
    protected abstract String resolveCurPlayer(PlatformConfig config, Object matchData);
    protected abstract String resolveMaxPlayer(PlatformConfig config, Object matchData);
    protected abstract String resolvePrice(PlatformConfig config, Object matchData);
    protected abstract String resolveLink(PlatformConfig config, Object matchData);
}
