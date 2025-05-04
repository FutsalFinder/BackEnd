package futsal.futsalMatch.service.strategy.request;

import futsal.futsalMatch.config.platform.PlatformConfig;
import futsal.futsalMatch.enums.Region;

import java.time.LocalDate;
import java.util.List;

public interface RequestStrategy {
    List<Object> request(PlatformConfig config, LocalDate date, Region region);
    String fetch(PlatformConfig config, LocalDate date, Region region);
}
